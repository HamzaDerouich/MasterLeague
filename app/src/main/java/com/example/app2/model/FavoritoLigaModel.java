package com.example.app2.model;

public class FavoritoLigaModel {
    private int ligaId;
    private String nombre;
    private String logoUrl;
    private int temporada;

    public FavoritoLigaModel(int ligaId, String nombre, String logoUrl, int temporada) {
        this.ligaId = ligaId;
        this.nombre = nombre;
        this.logoUrl = logoUrl;
        this.temporada = temporada;
    }

    public int getLigaId() { return ligaId; }
    public String getNombre() { return nombre; }
    public String getLogoUrl() { return logoUrl; }
    public int getTemporada() { return temporada; }
}