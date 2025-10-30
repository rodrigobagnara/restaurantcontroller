package com.restaurantcontroller.restaurantcontroller.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.ZonedDateTime;


@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s\\.\\-áàâãéêíóôõúçÁÀÂÃÉÊÍÓÔÕÚÇ]+$", message = "Nome deve conter apenas letras, espaços, hífens e apostrofes")
    @Column(nullable = false, length = 100)
    private String name;
    
    @NotBlank(message = "Identificação do usuário não pode ser nula ou vazia")
    @Pattern(regexp = "^(\\d{11}|\\d{9})$",
             message = "Identificação deve ser um CPF válido (XXXXXXXXXXX) ou RG válido (XXXXXXXXX)")
    @Column(nullable = false, unique = true, length = 14)
    private String userIdentification;
    
    @NotBlank(message = "Email não pode ser nulo ou vazio")
    @Email(message = "Email deve ter um formato válido")
    @Size(max = 100, message = "Email não pode exceder 100 caracteres")
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @NotNull(message = "Endereço é obrigatório")
    private Address addressUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_credentials_id")
    @NotNull(message = "Credenciais são obrigatórias")
    private UserCredentials userCredentials;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Perfil não pode ser nulo")
    @Column(nullable = false)
    private EProfile profile;
    
    @Column(name = "last_update")
    private ZonedDateTime lastUpdate;



    // Métodos getter e setter

    public Long getId() {
        return id;
    }

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

    public Address getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(Address addressUser) {
        this.addressUser = addressUser;
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }


    public EProfile getProfile() {
        return profile;
    }

    public void setProfile(EProfile profile) {
        this.profile = profile;
    }

    public ZonedDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
