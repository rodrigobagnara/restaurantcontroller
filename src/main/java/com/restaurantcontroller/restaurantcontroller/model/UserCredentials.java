package com.restaurantcontroller.restaurantcontroller.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.ZonedDateTime;

@Entity
@Table(name = "user_credentials")
public class UserCredentials {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank(message = "Username não pode ser nulo ou vazio")
    @Size(max = 50, message = "Username não pode exceder 50 caracteres")
    @Column(nullable = false, length = 50, unique = true)
    private String username;
    
    @NotBlank(message = "Password não pode ser nulo ou vazio")
    @Column(nullable = false)
    private String password;

    @Column(name = "last_update")
    private ZonedDateTime lastUpdate;


    // Métodos getter e setter

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZonedDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
