package com.example.app2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UsuarioModel implements Parcelable {
    private String nombre;
    private String mail;
    private String contrasena;

    public UsuarioModel() {
        // Default constructor required for calls to DataSnapshot.getValue(UsuarioModel.class)
    }

    public UsuarioModel(String nombre, String mail, String contrasena) {
        this.nombre = nombre;
        this.mail = mail;
        this.contrasena = contrasena;
    }

    protected UsuarioModel(Parcel in) {
        nombre = in.readString();
        mail = in.readString();
        contrasena = in.readString();
    }

    public static final Creator<UsuarioModel> CREATOR = new Creator<UsuarioModel>() {
        @Override
        public UsuarioModel createFromParcel(Parcel in) {
            return new UsuarioModel(in);
        }

        @Override
        public UsuarioModel[] newArray(int size) {
            return new UsuarioModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(mail);
        dest.writeString(contrasena);
    }

    // Getters and Setters
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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