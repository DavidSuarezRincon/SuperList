package com.david.superlist.pojos;

import java.util.ArrayList;

public class Lista {
    public int id;
    private int color;
    private String title;
    private String description;
    private String endDate;
    private String type;
    private String creationDate;
    private ArrayList<TareaLista> tasksList;

    public Lista(int id, int color, String title, String description, String endDate, String type, String creationDate, ArrayList<TareaLista> tasksList) {
        this.id = id;
        this.color = color;
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.type = type;
        this.creationDate = creationDate;
        this.tasksList = tasksList;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
