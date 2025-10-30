package com.restaurantcontroller.restaurantcontroller.dto;

import jakarta.validation.constraints.*;


public class UserCredentialsDTO {
    
    @NotBlank(message = "Username não pode ser nulo ou vazio")
    @Size(max = 50, message = "Username não pode exceder 50 caracteres")
    private String username;
    
    @NotBlank(message = "Password não pode ser nulo ou vazio")
    @Size(min = 8, max = 32, message = "Password deve ter entre 8 e 32 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$",
             message = "Password deve conter pelo menos: 1 letra minúscula, 1 letra maiúscula, 1 número e 1 caractere especial (@$!%*?&)")
    private String password;


    // Métodos getter e setter

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
