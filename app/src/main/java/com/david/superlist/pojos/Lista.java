package com.david.superlist.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Lista implements Parcelable {
    public int id;
    private int color;
    private String name;
    private String description;
    private String endDate;
    private String type;
    private String creationDate;
    private ArrayList<TareaLista> tasksList;

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
