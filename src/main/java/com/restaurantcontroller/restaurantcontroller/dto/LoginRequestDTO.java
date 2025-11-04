package com.restaurantcontroller.restaurantcontroller.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {
    @NotBlank(message = "Username não pode ser nulo ou vazio")
    private String username;

    @NotBlank(message = "Password não pode ser nulo ou vazio")
    private String password;

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
}

