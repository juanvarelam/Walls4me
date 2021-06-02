package com.jvamou.walls4me;

import java.io.Serializable;

public class Wallpaper implements Serializable {

    String url;

    public Wallpaper() {
    }

    public Wallpaper(String url) {
        this.url = url;
    }

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
