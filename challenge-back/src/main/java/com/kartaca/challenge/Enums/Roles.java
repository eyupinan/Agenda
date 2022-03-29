package com.kartaca.challenge.Enums;

public enum Roles {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");
    private final String type;

    private Roles(String type) {
        this.type = type;
    }
    public String toString(){
        return this.type;
    }
}