package com.david.superlist.pojos;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// Esta clase representa una Lista en la aplicación
public class Lista implements Parcelable {
    // Declaración de variables de instancia
    public int id;
    private int color;
    private String name;
    private String description;
    private String endDate;
    private String type;
    private String creationDate;
    private ArrayList<TareaLista> tasksList;

    // Constructor vacío
    public Lista() {

    }

    // Constructor con todos los atributos
    public Lista(int id, int color, String name, String description, String endDate, String type, String creationDate, ArrayList<TareaLista> tasksList) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.description = description;
        this.endDate = endDate;
        this.type = type;
        this.creationDate = creationDate;
        this.tasksList = tasksList;
    }

    // Método para crear una lista por defecto
    public static Lista nuevaListaDefault() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

            ArrayList<TareaLista> listaTareasDefault = new ArrayList<>();
            listaTareasDefault.add(TareaLista.nuevaTareaDefault());
            Lista lista = new Lista(0, generarColor(), "Lista de prueba", "Esta es tu primera lista",
                    fechaActual.plusDays(1).format(formatter),
                    "Lista de la Compra", fechaActual.format(formatter), listaTareasDefault);
            return lista;
        }

        return null;
    }

    // Método para generar un color aleatorio
    public static int generarColor() {
        int rojoRandom = (int) (Math.random() * 256);
        int verdeRandom = (int) (Math.random() * 256);
        int azulRandom = (int) (Math.random() * 256);

        return Color.argb(255, rojoRandom, verdeRandom, azulRandom);
    }

    // Constructor para crear una lista a partir de un Parcel
    protected Lista(Parcel in) {
        id = in.readInt();
        color = in.readInt();
        name = in.readString();
        description = in.readString();
        endDate = in.readString();
        type = in.readString();
        creationDate = in.readString();
        tasksList = in.createTypedArrayList(TareaLista.CREATOR);
    }

    // Creador para crear una lista a partir de un Parcel
    public static final Creator<Lista> CREATOR = new Creator<Lista>() {
        @Override
        public Lista createFromParcel(Parcel in) {
            return new Lista(in);
        }

        @Override
        public Lista[] newArray(int size) {
            return new Lista[size];
        }
    };

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public ArrayList<TareaLista> getTasksList() {
        return tasksList;
    }

    public void setTasksList(ArrayList<TareaLista> tasksList) {
        this.tasksList = tasksList;
    }

    // Métodos requeridos por la interfaz Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(color);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(endDate);
        dest.writeString(type);
        dest.writeString(creationDate);
        dest.writeTypedList(tasksList);
    }
}