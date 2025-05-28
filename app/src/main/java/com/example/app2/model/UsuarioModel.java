package com.example.app2.model;

import java.io.Serializable;

public class Usuario implements Serializable
{

    private String nombre;
    private String mail;
    private String contaseña;

    public Usuario()
    {

    }

    public Usuario(String nombre, String mail, String contaseña) {
        this.nombre = nombre;
        this.mail = mail;
        this.contaseña = contaseña;
    }

    public String getContaseña() {
        return contaseña;
    }

    public void setContaseña(String contaseña) {
        this.contaseña = contaseña;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
