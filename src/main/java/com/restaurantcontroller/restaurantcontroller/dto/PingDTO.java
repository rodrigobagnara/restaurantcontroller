package com.restaurantcontroller.restaurantcontroller.dto;

public class PingDTO {

    private String response;

    // Construtor padrão
    public PingDTO() {
        this.response = "pong";
    }

    // Métodos getter e setter
    public String getResponse() {
        return response;
    }
}
