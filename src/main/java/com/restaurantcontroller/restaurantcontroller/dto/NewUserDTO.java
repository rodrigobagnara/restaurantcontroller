package com.restaurantcontroller.restaurantcontroller.dto;

import com.restaurantcontroller.restaurantcontroller.model.EProfile;
import com.restaurantcontroller.restaurantcontroller.validation.UniqueEmail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserDTO {
    
    // Dados pessoais do usuário
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s\\.\\-áàâãéêíóôõúçÁÀÂÃÉÊÍÓÔÕÚÇ]+$", message = "Nome deve conter apenas letras, espaços, hífens e apostrofes")
    private String name;
    
    @NotBlank(message = "Identificação do usuário não pode ser nula ou vazia")
    @Pattern(regexp = "^(\\d{11}|\\d{9})$",
             message = "Identificação deve ser um CPF válido (XXXXXXXXXXX) ou RG válido (XXXXXXXXX)")
    private String userIdentification;
    
    @NotBlank(message = "Email não pode ser nulo ou vazio")
    @Email(message = "Email deve ter um formato válido")
    @Size(max = 100, message = "Email não pode exceder 100 caracteres")
    @UniqueEmail(message = "Email já está em uso")
    private String email;
    
    @NotNull(message = "Perfil não pode ser nulo")
    private EProfile profile;
    
    // Endereço do usuário
    @Valid
    @NotNull(message = "Endereço é obrigatório")
    private AddressDTO address;
    
    // Credenciais de acesso
    @Valid
    @NotNull(message = "Credenciais são obrigatórias")
    private UserCredentialsDTO credentials;

    
    // Métodos getter (caso o Lombok não esteja funcionando)
    
    public String getName() {
        return name;
    }
    
    public String getUserIdentification() {
        return userIdentification;
    }
    
    public String getEmail() {
        return email;
    }
    
    public EProfile getProfile() {
        return profile;
    }
    
    public AddressDTO getAddress() {
        return address;
    }
    
    public UserCredentialsDTO getCredentials() {
        return credentials;
    }
}
