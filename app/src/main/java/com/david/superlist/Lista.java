package com.david.superlist;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Lista {

    public int color;
    private String titulo;
    private String descripcion;
    private String fechaFin;
    private String tipo;
    private String fechaCreacion;

    private ArrayList<String> itemsLista;

    public Lista(int color, String titulo, String descripcion, String fechaFin, String tipo, String fechaCreacion, ArrayList<String> itemsLista) {

        this.color = color;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.tipo = tipo;
        this.fechaCreacion = fechaCreacion;
        this.itemsLista = itemsLista;

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

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public String getFecha() {
        return fechaCreacion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaCreacion(String fecha) {
        this.fechaCreacion = fecha;
    }

    public void setColor(int color) {
        this.color = color;
    }

//    public static ArrayList<Lista> getTestListas() {
//        ArrayList<Lista> testListas = new ArrayList<>();
//        testListas.add(new Lista(1, "Test 1", "Soy una prueba", "22-01-2024", "Lista compra", "01-12-2023"));
//        testListas.add(new Lista(2, "Test 2", "Soy una segunda prueba", "22-01-2024", "Lista compra", "01-12-2023"));
//        return testListas;
//    }
}
