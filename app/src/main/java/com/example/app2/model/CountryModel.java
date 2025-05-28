package com.example.app2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CountryModel implements Parcelable {
    private String name;
    private String code;
    private String flag;

    // Constructor vacío
    public CountryModel() {}

    // Constructor con parámetros
    public CountryModel(String name, String code, String flag) {
        this.name = name;
        this.code = code;
        this.flag = flag;
    }

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }

    // Métodos de Parcelable
    protected CountryModel(Parcel in) {
        name = in.readString();
        code = in.readString();
        flag = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(flag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Creator estático para crear el objeto desde un Parcel
    public static final Creator<CountryModel> CREATOR = new Creator<CountryModel>() {
        @Override
        public CountryModel createFromParcel(Parcel in) {
            return new CountryModel(in);
        }

        @Override
        public CountryModel[] newArray(int size) {
            return new CountryModel[size];
        }
    };
}
