package com.david.superlist.pojos;

import java.util.ArrayList;

public class Lista {

    public int id;
    private int color;
    private String titulo;
    private String descripcion;
    private String fechaFin;
    private String tipo;
    private String fechaCreacion;
    private ArrayList<TareaLista> itemsLista;

    public Lista(int id, int color, String titulo, String descripcion, String fechaFin, String tipo, String fechaCreacion, ArrayList<TareaLista> itemsLista) {
        this.id = id;
        this.color = color;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.tipo = tipo;
        this.fechaCreacion = fechaCreacion;
        this.itemsLista = itemsLista;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public ArrayList<TareaLista> getItemsLista() {
        return itemsLista;
    }

    public void setItemsLista(ArrayList<TareaLista> itemsLista) {
        this.itemsLista = itemsLista;
    }

    //    public static ArrayList<Lista> getTestListas() {
//        ArrayList<Lista> testListas = new ArrayList<>();
//        testListas.add(new Lista(1, "Test 1", "Soy una prueba", "22-01-2024", "Lista compra", "01-12-2023"));
//        testListas.add(new Lista(2, "Test 2", "Soy una segunda prueba", "22-01-2024", "Lista compra", "01-12-2023"));
//        return testListas;
//    }
}
