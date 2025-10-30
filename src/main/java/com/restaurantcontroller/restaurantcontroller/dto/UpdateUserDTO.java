package com.restaurantcontroller.restaurantcontroller.dto;

import com.restaurantcontroller.restaurantcontroller.model.EProfile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateUserDTO {
    
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres")
    private String name;
    
    @NotBlank(message = "Identificação do usuário não pode ser nula ou vazia")
    @Size(max = 20, message = "Identificação do usuário não pode exceder 20 caracteres")
    private String userIdentification;
    
    @NotBlank(message = "Email não pode ser nulo ou vazio")
    @Email(message = "Email deve ter um formato válido")
    @Size(max = 100, message = "Email não pode exceder 100 caracteres")
    private String email;
    
    @NotNull(message = "Perfil não pode ser nulo")
    private EProfile profile;
    
    @Valid
    private AddressDTO address;

    // Construtor padrão
    public UpdateUserDTO() {}

    // Construtor com parâmetros
    public UpdateUserDTO(String name, String userIdentification, String email, EProfile profile, AddressDTO address) {
        this.name = name;
        this.userIdentification = userIdentification;
        this.email = email;
        this.profile = profile;
        this.address = address;
    }

    // Métodos getter e setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserIdentification() {
        return userIdentification;
    }

    public void setUserIdentification(String userIdentification) {
        this.userIdentification = userIdentification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EProfile getProfile() {
        return profile;
    }

    public void setProfile(EProfile profile) {
        this.profile = profile;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
