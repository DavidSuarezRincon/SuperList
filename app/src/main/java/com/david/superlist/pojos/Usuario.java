package com.david.superlist.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

// Esta clase representa un Usuario en la aplicación
public class Usuario implements Parcelable {

    // Declaración de variables de instancia
    // rol: 0 para usuario, 1 para administrador
    private int rol;
    private ArrayList<Lista> userLists = new ArrayList<>();

    // Constructor vacío
    public Usuario() {

    }

    // Constructor con los atributos rol y listas
    public Usuario(int rol, ArrayList<Lista> listas) {
        this.rol = rol;
        this.userLists = listas;
    }

    // Constructor para crear un Usuario a partir de un Parcel
    protected Usuario(Parcel in) {
        rol = in.readInt();
        userLists = in.createTypedArrayList(Lista.CREATOR);
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

    // Getters y setters
    public ArrayList<Lista> getUserLists() {
        return userLists;
    }

    public void setUserLists(ArrayList<Lista> userLists) {
        this.userLists = userLists;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    // Métodos requeridos por la interfaz Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(rol);
        dest.writeTypedList(userLists);
    }
}