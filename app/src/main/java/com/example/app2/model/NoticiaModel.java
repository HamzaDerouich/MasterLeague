package com.example.app2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NoticiaModel implements Parcelable {
    private String author;
    private String title;
    private String description;
    private String url;
    private String source;
    private String image;
    private String category;
    private String language;
    private String country;
    private String published_at;

    // Constructor vacío
    public NoticiaModel() {
    }

    // Constructor con parámetros
    public NoticiaModel(String author, String title, String description, String url,
                        String source, String image, String category, String language,
                        String country, String published_at) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.source = source;
        this.image = image;
        this.category = category;
        this.language = language;
        this.country = country;
        this.published_at = published_at;
    }

    // Constructor Parcelable
    protected NoticiaModel(Parcel in) {
        author = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        source = in.readString();
        image = in.readString();
        category = in.readString();
        language = in.readString();
        country = in.readString();
        published_at = in.readString();
    }

    public static final Creator<NoticiaModel> CREATOR = new Creator<NoticiaModel>() {
        @Override
        public NoticiaModel createFromParcel(Parcel in) {
            return new NoticiaModel(in);
        }

        @Override
        public NoticiaModel[] newArray(int size) {
            return new NoticiaModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(source);
        dest.writeString(image);
        dest.writeString(category);
        dest.writeString(language);
        dest.writeString(country);
        dest.writeString(published_at);
    }

    // Getters y Setters (se mantienen igual que en tu versión original)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }
}