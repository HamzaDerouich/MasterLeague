package com.example.app2.model;

import java.io.Serializable;

public class EquipoClasificacionModel implements Serializable {

        private int rank;
        private String nombreEquipo;
        private String logoUrl;
        private int puntos;
        private int golesFavor;
        private int golesContra;
        private int partidosJugados;
        private int partidosGanados;
        private int partidosEmpatados;
        private int partidosPerdidos;
        private int diferenciaGoles;


    public EquipoClasificacionModel() {

    }

    public class EquipoClasificacion {
        private int rank;
        private String nombreEquipo;
        private String logoUrl;
        private int puntos;
        private int golesFavor;
        private int golesContra;
        private int partidosJugados;
        private int partidosGanados;
        private int partidosEmpatados;
        private int partidosPerdidos;
        private int diferenciaGoles;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(int partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public void setPartidosEmpatados(int partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados;
    }

    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public void setPartidosPerdidos(int partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos;
    }

    public int getDiferenciaGoles() {
        return diferenciaGoles;
    }

    public void setDiferenciaGoles(int diferenciaGoles) {
        this.diferenciaGoles = diferenciaGoles;
    }

    @Override
    public String toString() {
        return "EquipoClasificacionModel{" +
                "rank=" + rank +
                ", nombreEquipo='" + nombreEquipo + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", puntos=" + puntos +
                ", golesFavor=" + golesFavor +
                ", golesContra=" + golesContra +
                ", partidosJugados=" + partidosJugados +
                ", partidosGanados=" + partidosGanados +
                ", partidosEmpatados=" + partidosEmpatados +
                ", partidosPerdidos=" + partidosPerdidos +
                ", diferenciaGoles=" + diferenciaGoles +
                '}';
    }
}
