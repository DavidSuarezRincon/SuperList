package com.david.superlist.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TareaLista implements Parcelable {

    private String tarea;
    private String prioridad;

    private int colorIconoPrioridad;

    public TareaLista(String tarea, String prioridad) {
        this.tarea = tarea;
        this.prioridad = prioridad;
    }

    private TareaLista(Parcel in) {
        tarea = in.readString();
        prioridad = in.readString();
        colorIconoPrioridad = in.readInt();
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public int getColorIconoPrioridad() {
        return colorIconoPrioridad;
    }

    public void setColorIconoPrioridad(int colorIconoPrioridad) {
        this.colorIconoPrioridad = colorIconoPrioridad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(tarea);
        dest.writeString(prioridad);
        dest.writeInt(colorIconoPrioridad);
    }

    public static final Parcelable.Creator<TareaLista> CREATOR = new Parcelable.Creator<TareaLista>() {
        @Override
        public TareaLista createFromParcel(Parcel in) {
            // Read fields from parcel and create your object

            return new TareaLista(in);
        }

        @Override
        public TareaLista[] newArray(int size) {
            return new TareaLista[size];
        }
    };
}
