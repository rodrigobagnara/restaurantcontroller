package com.restaurantcontroller.restaurantcontroller.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.restaurantcontroller.restaurantcontroller.service.UserCredentialsService;
import org.springframework.http.ResponseEntity;
import com.restaurantcontroller.restaurantcontroller.dto.UpdateUsernameDTO;
import com.restaurantcontroller.restaurantcontroller.dto.UpdatePasswordDTO;
import com.restaurantcontroller.restaurantcontroller.dto.LoginRequestDTO;
import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/credentials")
@Tag(name = "4. Credenciais", description = "Operações de gerenciamento de credenciais de usuários")
public class UserCredentialsController {
    
    private final UserCredentialsService userCredentialsService;
    private final PasswordEncoder passwordEncoder;

    public UserCredentialsController(UserCredentialsService userCredentialsService, PasswordEncoder passwordEncoder) {
        this.userCredentialsService = userCredentialsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Atualiza o username de um usuário
     * @param id ID do usuário
     * @param username Novo username
     * @return ResponseEntity com a mensagem de sucesso ou erro
     */
    @PutMapping("/{id}/username")
    @Operation(summary = "Atualizar username", description = "Atualiza o username de um usuário")
    public ResponseEntity<String> updateUserName(@PathVariable Long id, @RequestBody @Valid UpdateUsernameDTO body) {
        try {
            userCredentialsService.updateUsername(id, body.getUsername());
            return ResponseEntity.ok(String.format("Username (%s) atualizado com sucesso!", body.getUsername()));
        } catch (IllegalArgumentException e) {
            System.err.println("(UserCredentialsController.updateUserName) Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            System.err.println("(UserCredentialsController.updateUserName) Error: " + e.getMessage());
            if (e.getMessage() != null && e.getMessage().contains("não encontradas")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Atualiza a password de um usuário
     * @param id ID do usuário
     * @return ResponseEntity com a mensagem de sucesso ou erro
     */
    @PutMapping("/{id}/password")
    @Operation(summary = "Atualizar password", description = "Atualiza a password de um usuário")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody @Valid UpdatePasswordDTO body) {
        try {
            userCredentialsService.updatePassword(id, body.getPassword());
            return ResponseEntity.ok("Password atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("(UserCredentialsController.updatePassword) Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            System.err.println("(UserCredentialsController.updatePassword) Error: " + e.getMessage());
            if (e.getMessage() != null && e.getMessage().contains("não encontradas")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Realiza o login de um usuário
     * @param loginRequest Objeto contendo username e password
     * @return ResponseEntity com a mensagem de sucesso ou erro
     */
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Verifica se as credenciais de login são válidas")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        try {
            var userOpt = userCredentialsService.findByUsername(loginRequest.getUsername());

            if (userOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
                return ResponseEntity.ok("Login realizado com sucesso!");
            } else {
                return ResponseEntity.status(401).body("Credenciais inválidas");
            }
        } catch (Exception e) {
            System.err.println("(UserCredentialsController.login) Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


}
