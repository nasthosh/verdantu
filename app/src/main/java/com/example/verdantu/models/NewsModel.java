package com.example.verdantu.models;

import android.media.Image;

import java.net.URL;

public class NewsModel {

String title;
String description;
URL url;


    public NewsModel(String title, String description, URL url) {
        this.title = title;
        this.description = description;
        this.url = url;

    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url=" + url +
                '}';
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

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

}
