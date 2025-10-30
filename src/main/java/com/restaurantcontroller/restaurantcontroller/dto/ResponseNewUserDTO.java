package com.restaurantcontroller.restaurantcontroller.dto;

import com.restaurantcontroller.restaurantcontroller.model.EProfile;

import java.time.ZonedDateTime;


public class ResponseNewUserDTO {

    // ID do usuário
    private Long id;
    
    // Dados pessoais do usuário
    private String name;
    private String userIdentification;
    private String email;
    private String username;

    // Perfil do usuário
    private EProfile profile;
    
    // Endereço do usuário
    private AddressDTO address;
    
    // Data da última atualização
    private java.time.ZonedDateTime lastUpdate;


    // Métodos getter e setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public ZonedDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
