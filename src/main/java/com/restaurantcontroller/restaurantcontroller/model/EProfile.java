package com.restaurantcontroller.restaurantcontroller.model;

public enum EProfile {
    client("Cliente"),
    owner("Proprietário"),
    admin("Administrador");

    private String desciption;

    EProfile(String desciption) {
        this.desciption = desciption;
    }

    public String getDesciption() {
        return desciption;
    }

}

