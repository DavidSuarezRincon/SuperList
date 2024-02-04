package com.david.superlist.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;

public class Usuario implements Parcelable {


    private int rol; // 0 user 1 admin
    private ArrayList<Lista> userLists = new ArrayList<>();

    public Usuario(){

    }
    public Usuario( int rol, ArrayList<Lista> listas) {
        this.rol = rol;
        this.userLists = listas;
    }

    protected Usuario(Parcel in) {
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

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }


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
