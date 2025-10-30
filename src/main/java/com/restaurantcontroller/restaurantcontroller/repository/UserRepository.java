package com.restaurantcontroller.restaurantcontroller.repository;

import com.restaurantcontroller.restaurantcontroller.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUserIdentification(String userIdentification);
    
    boolean existsByEmail(String email);
    
    boolean existsByUserIdentification(String userIdentification);
    
    List<User> findByNameContainingIgnoreCase(String name);
}
