package com.example.project.project.models;

public class ModelUser {
    private int id;
    private String email;
    private String password;

    public ModelUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return password;
    }

    public void setToken(String token) {
        this.password = token;
    }
}
