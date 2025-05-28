package com.example.app2.model;

import java.io.Serializable;

public class Goleador implements Serializable {
    String nombre;
    int imagen;
    String descripcion;
    String goles;

    public Goleador(String nombre, int imagen, String descripcion , String goles) {
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.nombre = nombre;
        this.goles = goles;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGoles() {
        return goles;
    }

}
