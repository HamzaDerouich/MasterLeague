package com.example.app2.model;

import java.io.Serializable;

public class Liga implements Serializable {
    String id;
    String nombre;
    String imagenUrl;
    String descripcion;
    String pais;
    String año_fundada;
    String codigoPais;

    public Liga() {}

    public Liga(String id, String nombre, String imagenUrl, String descripcion,
                String pais, String año_fundada, String codigoPais) {
        this.id = id;
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
        this.pais = pais;
        this.año_fundada = año_fundada;
        this.codigoPais = codigoPais;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getImagenUrl() { return imagenUrl; }
    public String getDescripcion() { return descripcion; }
    public String getPais() { return pais; }
    public String getAño_fundada() { return año_fundada; }
    public String getCodigoPais() { return codigoPais; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    // Añade más setters si los necesitas
}