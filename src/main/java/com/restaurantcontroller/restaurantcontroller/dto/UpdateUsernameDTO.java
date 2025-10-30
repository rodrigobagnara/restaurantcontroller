package com.restaurantcontroller.restaurantcontroller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUsernameDTO {
    
    @NotBlank(message = "Username não pode ser nulo ou vazio")
    @Size(max = 50, message = "Username não pode exceder 50 caracteres")
    private String username;

    // Construtor padrão
    public UpdateUsernameDTO() {}

    // Construtor com parâmetros
    public UpdateUsernameDTO(String username) {
        this.username = username;
    }

    // Métodos getter e setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
