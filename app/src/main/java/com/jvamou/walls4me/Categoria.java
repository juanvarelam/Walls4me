package com.jvamou.walls4me;

public class Categoria {

    String url;

    public Categoria() {
    }

    public Categoria(String url) {
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
        return "Categoria{" +
                "url='" + url + '\'' +
                '}';
    }
}
