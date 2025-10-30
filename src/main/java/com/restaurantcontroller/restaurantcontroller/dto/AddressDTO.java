package com.restaurantcontroller.restaurantcontroller.dto;

import jakarta.validation.constraints.*;


public class AddressDTO {
    
    @NotBlank(message = "Rua não pode ser nula ou vazia")
    @Size(max = 255, message = "Rua não pode exceder 255 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\.\\-áàâãéêíóôõúçÁÀÂÃÉÊÍÓÔÕÚÇ]+$", message = "Rua pode conter apenas caracteres alfanuméricos, espaços, pontos, hífens, acentuações e ç")
    private String street;

    @NotNull(message = "Número não pode ser nulo")
    @Min(value = 1, message = "Número deve ser maior que zero")
    private Integer number;

    @NotBlank(message = "Complemento não pode ser nulo ou vazio")
    @Size(max = 50, message = "Complemento não pode exceder 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\.\\-áàâãéêíóôõúçÁÀÂÃÉÊÍÓÔÕÚÇ]+$", message = "Complemento pode conter apenas caracteres alfanuméricos, espaços, pontos, hífens, acentuações e ç")
    private String complement;

    @NotBlank(message = "Bairro não pode ser nulo ou vazio")
    @Size(max = 50, message = "Bairro não pode exceder 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\.\\-áàâãéêíóôõúçÁÀÂÃÉÊÍÓÔÕÚÇ]+$", message = "Bairro pode conter apenas caracteres alfanuméricos, espaços, pontos, hífens, acentuações e ç")
    private String neighborhood;

    @NotBlank(message = "Cidade não pode ser nula ou vazia")
    @Size(max = 50, message = "Cidade não pode exceder 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\.\\-áàâãéêíóôõúçÁÀÂÃÉÊÍÓÔÕÚÇ]+$", message = "Cidade pode conter apenas caracteres alfanuméricos, espaços, pontos, hífens, acentuações e ç")
    private String city;

    @NotBlank(message = "Estado não pode ser nulo ou vazio")
    @Size(max = 50, message = "Estado não pode exceder 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s\\.\\-áàâãéêíóôõúçÁÀÂÃÉÊÍÓÔÕÚÇ]+$", message = "Estado pode conter apenas letras, espaços, pontos, hífens, acentuações e ç")
    private String state;

    @NotBlank(message = "País não pode ser nulo ou vazio")
    @Size(max = 50, message = "País não pode exceder 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s\\.\\-áàâãéêíóôõúçÁÀÂÃÉÊÍÓÔÕÚÇ]+$", message = "País pode conter apenas letras, espaços, pontos, hífens, acentuações e ç")
    private String country;

    @NotBlank(message = "CEP não pode ser nulo ou vazio")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP deve seguir o formato XXXXX-XXX")
    private String cep;


    
    // Métodos getter e setter

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
