package com.david.superlist.pojos;

public class Usuario {

    private static int id;
    private String email;
    private String password;
    private int rol; // 0 user 1 admin

    public Usuario(int id, String email, String password, int rol) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public boolean hasThisUser(String email) {
        return this.email.equalsIgnoreCase(email);
    }

    public boolean hasThisPassword(String password) {
        return this.password.equals(password);
    }
}
