package com.david.superlist.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

// Esta clase representa un Usuario en la aplicación
public class Usuario implements Parcelable {

    // Declaración de variables de instancia
    // rol: 0 para usuario, 1 para administrador
    private String id;
    private int role;
    private ArrayList<Lista> userLists = new ArrayList<>();
    private String nombre;
    private String email;
    private boolean baned; //Si esta baneado true.

    // Constructor vacío
    public Usuario() {
        userLists = new ArrayList<>();
    }

    // Constructor con los atributos rol y listas
    public Usuario(String id, int role, ArrayList<Lista> listas, String nombre, String email, boolean baned) {
        this.id = id;
        this.role = role;
        this.userLists = listas;
        this.nombre = nombre;
        this.email = email;
        this.baned = baned;
    }

    // Constructor para crear un Usuario a partir de un Parcel
    protected Usuario(Parcel in) {
        id = in.readString();
        role = in.readInt();
        userLists = in.createTypedArrayList(Lista.CREATOR);
        nombre = in.readString();
        email = in.readString();
        baned = (in.readInt() == 1); // Si es 1 es que está baneado.
        Log.i("valorIsBaned", "" + baned);
    }

    // Creador para crear un Usuario a partir de un Parcel
    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    // Setters

    public String getId() {
        return id;
    }

    public void setUserLists(ArrayList<Lista> userLists) {
        this.userLists = userLists;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setBaned(boolean baned) {
        this.baned = baned;
    }

    // Getters

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Lista> getUserLists() {
        return userLists;
    }

    public String getNombre() {
        return nombre;
    }

    public int getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public boolean isBaned() {
        return baned;
    }

    // Métodos requeridos por la interfaz Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(role);
        dest.writeTypedList(userLists);
        dest.writeString(nombre);
        dest.writeString(email);
        dest.writeInt((baned) ? 1 : 0);
    }
}