package com.restaurantcontroller.restaurantcontroller.controller;

import com.restaurantcontroller.restaurantcontroller.dto.AddressDTO;
import com.restaurantcontroller.restaurantcontroller.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/addresses")
@Tag(name = "3. Endereços", description = "Operações de gerenciamento de endereços")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Buscar endereço por ID do usuário", description = "Retorna o endereço de um usuário específico pelo ID do usuário")
    public ResponseEntity<AddressDTO> getAddressByUserId(@PathVariable Long userId) {
        try {
            Optional<AddressDTO> address = addressService.getAddressByUserId(userId);
            return address.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            System.err.println("(AddressController.getAddressByUserId) Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            System.err.println("(AddressController.getAddressByUserId) Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/user/{userId}")
    @Operation(summary = "Atualizar endereço do usuário", description = "Atualiza o endereço de um usuário existente")
    public ResponseEntity<AddressDTO> updateAddressByUserId(@PathVariable Long userId, @Valid @RequestBody AddressDTO addressDTO) {
        try {
            AddressDTO updatedAddress = addressService.updateAddressByUserId(userId, addressDTO);
            return ResponseEntity.ok(updatedAddress);
        } catch (IllegalArgumentException e) {
            System.err.println("(AddressController.updateAddressByUserId) Error: " + e.getMessage());
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            System.err.println("(AddressController.updateAddressByUserId) Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
