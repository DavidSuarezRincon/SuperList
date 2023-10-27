package com.david.superlist;

public class Lista {

    public String color;
    private String titulo;
    private String descripcion;
    private String fecha;

    public Lista(String color, String titulo, String descripcion, String fecha) {
        this.color = color;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getColor() {
        return color;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
