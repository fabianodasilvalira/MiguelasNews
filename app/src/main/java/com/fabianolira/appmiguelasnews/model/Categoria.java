package com.fabianolira.appmiguelasnews.model;

public class Categoria {

    private String idCategoria;
    private String nomeCategoria;
    private String urlCategoria;
    private String imgCategoria;

    public Categoria() {
    }

    public Categoria(String nomeCategoria, String imgCategoria) {
        this.nomeCategoria = nomeCategoria;
        this.imgCategoria = imgCategoria;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getUrlCategoria() {
        return urlCategoria;
    }

    public void setUrlCategoria(String urlCategoria) {
        this.urlCategoria = urlCategoria;
    }

    public String getImgCategoria() {
        return imgCategoria;
    }

    public void setImgCategoria(String imgCategoria) {
        this.imgCategoria = imgCategoria;
    }
}
