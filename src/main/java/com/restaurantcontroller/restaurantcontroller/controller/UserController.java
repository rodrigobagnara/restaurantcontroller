package com.restaurantcontroller.restaurantcontroller.controller;

import com.restaurantcontroller.restaurantcontroller.dto.NewUserDTO;
import com.restaurantcontroller.restaurantcontroller.dto.ResponseNewUserDTO;
import com.restaurantcontroller.restaurantcontroller.dto.UpdateUserDTO;
import com.restaurantcontroller.restaurantcontroller.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "2. Usuários", description = "Operações de gerenciamento de usuários")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário com todos os dados (dados pessoais, endereço e credenciais)")
    public ResponseEntity<ResponseNewUserDTO> createUser(@Valid @RequestBody NewUserDTO newUsertDTO) {
        ResponseNewUserDTO createdUser = userService.createUser(newUsertDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna todos os usuários cadastrados")
    public ResponseEntity<List<ResponseNewUserDTO>> getAllUsers() {
        List<ResponseNewUserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo ID")
    public ResponseEntity<ResponseNewUserDTO> getUserById(@PathVariable Long id) {
        Optional<ResponseNewUserDTO> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    @Operation(summary = "Buscar usuários por nome", description = "Retorna uma lista de usuários cujo nome contém o termo buscado (busca parcial, case insensitive)")
    public ResponseEntity<List<ResponseNewUserDTO>> searchUsersByName(@RequestParam String name) {
        List<ResponseNewUserDTO> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza um usuário existente (não altera credenciais)")
    public ResponseEntity<ResponseNewUserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        ResponseNewUserDTO updatedUser = userService.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Deletar um usuário do sistema e retornar seus dados")
    public ResponseEntity<ResponseNewUserDTO> deleteUser(@PathVariable Long id) {
        Optional<ResponseNewUserDTO> deletedUser = userService.deleteUser(id);
        return deletedUser.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
