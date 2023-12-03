package com.david.superlist;

public class TareaLista {

    private String tarea;
    private String prioridad;

    private int colorIconoPrioridad;

    public TareaLista(String tarea, String prioridad) {
        this.tarea = tarea;
        this.prioridad = prioridad;
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
}
