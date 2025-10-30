package com.restaurantcontroller.restaurantcontroller.service;

import com.restaurantcontroller.restaurantcontroller.model.User;
import com.restaurantcontroller.restaurantcontroller.model.UserCredentials;
import com.restaurantcontroller.restaurantcontroller.repository.UserCredentialsRepository;
import com.restaurantcontroller.restaurantcontroller.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;

@Service
public class UserCredentialsService {

    // Repositório de credenciais
    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // Construtor para injeção de dependência
    public UserCredentialsService(UserCredentialsRepository userCredentialsRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Atualiza o username de um usuário
     * @param id ID do usuário
     * @param username Novo username
     * @throws IllegalArgumentException se o ID das credenciais for nulo ou o username for inválido
     * @throws RuntimeException se ocorrer um erro interno ao atualizar o username
     */
    public void updateUsername(Long id, String username) {
        try {

            if (id == null) 
                throw new IllegalArgumentException("ID do usuário não pode ser nulo");

            if (username == null || username.trim().isEmpty()) 
                throw new IllegalArgumentException("Username inválido");

            String newUsername = username.trim();

            if (userCredentialsRepository.existsByUsername(newUsername)) 
                throw new IllegalArgumentException("Username já está em uso");

            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            UserCredentials credentials = user.getUserCredentials();
            if (credentials == null) {
                throw new RuntimeException("Credenciais não encontradas");
            }

            credentials.setUsername(newUsername);
            credentials.setLastUpdate(ZonedDateTime.now());
            userCredentialsRepository.save(credentials);
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            System.err.println("(UserCredentialsService.updateUsername) Error: " + ex.getMessage());
            throw new RuntimeException("Erro interno ao atualizar credenciais: " + ex.getMessage(), ex);
        }
    }

    /**
     * Atualiza a password de um usuário
     * @param id ID do usuário
     * @param password Nova password
     * @throws IllegalArgumentException se o ID das credenciais for nulo ou a password for inválida
     * @throws RuntimeException se ocorrer um erro interno ao atualizar a password
     */
    public void updatePassword(Long id, String password) {
        try {

            if (id == null) 
                throw new IllegalArgumentException("ID do usuário não pode ser nulo");

            if (password == null || password.trim().isEmpty()) 
                throw new IllegalArgumentException("Password inválido");

            String newPassword = password.trim();

            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            UserCredentials credentials = user.getUserCredentials();
            if (credentials == null) {
                throw new RuntimeException("Credenciais não encontradas");
            }

            // Atualiza somente a senha (criptografada) e a data de atualização
            credentials.setPassword(passwordEncoder.encode(newPassword));
            credentials.setLastUpdate(ZonedDateTime.now());
            userCredentialsRepository.save(credentials);
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            System.err.println("(UserCredentialsService.updatePassword) Error: " + ex.getMessage());
            throw new RuntimeException("Erro interno ao atualizar senha: " + ex.getMessage(), ex);
        }
    }


}


