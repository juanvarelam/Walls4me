package com.jvamou.walls4me.Models;

public class Wallpaper {

    //Vars
    public String url;

    //Constructores
    public Wallpaper() {
    }

    public Wallpaper(String url) {
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
        return "Wallpaper{" +
                "url='" + url + '\'' +
                '}';
    }
}
