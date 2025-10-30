package com.restaurantcontroller.restaurantcontroller.repository;

import com.restaurantcontroller.restaurantcontroller.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    
    /**
     * Busca credenciais por username
     * @param username o username a ser buscado
     * @return Optional contendo as credenciais se encontradas
     */
    Optional<UserCredentials> findByUsername(String username);
    
    /**
     * Verifica se existe credenciais com o username informado
     * @param username o username a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByUsername(String username);

    
}
