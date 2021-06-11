package com.jvamou.walls4me.Models;

public class Categoria {

    //Vars
    public String url;

    //Constructorres
    public Categoria() {
    }

    public Categoria(String url) {
        this.url = url;
    }

    //Getters & Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "url='" + url + '\'' +
                '}';
    }
}
