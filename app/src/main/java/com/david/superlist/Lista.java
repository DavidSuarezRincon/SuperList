package com.david.superlist;

public class Lista {

    public int color;
    private String titulo;
    private String descripcion;
    private String fecha;

    public Lista(int color, String titulo, String descripcion, String fecha) {
        this.color = color;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getColor() {
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
    public void setColor(int color) {
        this.color = color;
    }
}
