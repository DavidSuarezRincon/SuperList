package com.david.superlist.pojos;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.david.superlist.R;

import java.util.Objects;

// Esta clase representa una TareaLista en la aplicación
public class TareaLista implements Parcelable {

    // Declaración de variables de instancia
    private String task;
    private String priority;
    private int iconPriorityColor;
    private boolean isChecked;

    // Constructor vacío
    public TareaLista() {

    }

    // Constructor con los atributos task y priority
    public TareaLista(String task, String priority) {
        this.task = task;
        this.priority = priority;
        this.isChecked = false;
    }

    // Constructor para crear una TareaLista a partir de un Parcel
    private TareaLista(Parcel in) {
        task = in.readString();
        priority = in.readString();
        iconPriorityColor = in.readInt();
        isChecked = in.readInt() != 0;
    }

    // Método para crear una TareaLista por defecto
    public static TareaLista nuevaTareaDefault(Context context) {
        return new TareaLista("Es una tarea de prueba.", context.getResources().getString(R.string.textoPrioridadAlta));
    }

    // Getters y setters
    public String getTask() {
        return task;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getIconPriorityColor() {
        return iconPriorityColor;
    }

    public void setIconPriorityColor(int iconPriorityColor) {
        this.iconPriorityColor = iconPriorityColor;
    }

    // Métodos requeridos por la interfaz Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(task);
        dest.writeString(priority);
        dest.writeInt(iconPriorityColor);
        dest.writeInt(isChecked ? 1 : 0); // Write isChecked as an integer
    }

    // Creador para crear una TareaLista a partir de un Parcel
    public static final Parcelable.Creator<TareaLista> CREATOR = new Parcelable.Creator<TareaLista>() {
        @Override
        public TareaLista createFromParcel(Parcel in) {
            return new TareaLista(in);
        }

        @Override
        public TareaLista[] newArray(int size) {
            return new TareaLista[size];
        }
    };

    // Método para comparar la igualdad de dos objetos TareaLista
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TareaLista)) return false;
        TareaLista that = (TareaLista) o;
        return getIconPriorityColor() == that.getIconPriorityColor() && Objects.equals(getTask(), that.getTask()) && Objects.equals(getPriority(), that.getPriority());
    }

    // Método para obtener el código hash de la TareaLista
    @Override
    public int hashCode() {
        return Objects.hash(getTask(), getPriority());
    }
}