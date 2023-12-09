package com.david.superlist.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;

public class Usuario implements Parcelable {

    private static int id;
    private String email;
    private String password;
    private int rol; // 0 user 1 admin
    private ArrayList<Lista> userLists = new ArrayList<>();
    private File saveData;

    public Usuario(int id, String email, String password, int rol) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    protected Usuario(Parcel in) {
        email = in.readString();
        password = in.readString();
        rol = in.readInt();
        userLists = in.createTypedArrayList(Lista.CREATOR);
    }

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

    public ArrayList<Lista> getUserLists() {
        return userLists;
    }

    public void setUserLists(ArrayList<Lista> userLists) {
        this.userLists = userLists;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
        dest.writeInt(rol);
        dest.writeTypedList(userLists);
    }
}
