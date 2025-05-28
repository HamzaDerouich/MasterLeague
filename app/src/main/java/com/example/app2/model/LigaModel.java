package com.example.app2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LigaModel implements Parcelable {
    private int id;
    private String name;
    private String type;
    private String logo;

    private CountryModel country;

    // Constructor vacío
    public LigaModel() {}

    // Constructor con parámetros
    public LigaModel(int id, String name, String type, String logo, CountryModel country) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.logo = logo;
        this.country = country;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public CountryModel getCountry() {
        return country;
    }

    public void setCountry(CountryModel country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "LigaModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", logo='" + logo + '\'' +
                ", country=" + country +
                '}';
    }

    // Métodos de Parcelable
    protected LigaModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readString();
        logo = in.readString();
        country = in.readParcelable(CountryModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(logo);
        dest.writeParcelable((Parcelable) country, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Creator estático para crear el objeto desde un Parcel
    public static final Creator<LigaModel> CREATOR = new Creator<LigaModel>() {
        @Override
        public LigaModel createFromParcel(Parcel in) {
            return new LigaModel(in);
        }

        @Override
        public LigaModel[] newArray(int size) {
            return new LigaModel[size];
        }
    };
}
