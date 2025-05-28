package com.example.tranbaongoc_k224111494_m02.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Account implements Serializable {
    private int id;
    private String username;
    private String password;

    public Account(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    @NonNull
    @Override
    public String toString() {
        return id + " - " + username;
    }
}

