package com.fabianolira.appmiguelasnews.model;

public class Noticia {
    private String idCategoria;
    private String categoria;
    private int imgCategoria;
    private String idNoticia;
    private String tituloNoticia;
    private String descricaoNoticia;
    private String dataNoticia;
    private int imgNoticia;

    public Noticia() {
    }



    public Noticia(String tituloNoticia, String dataNoticia, int imgNoticia) {
        this.tituloNoticia = tituloNoticia;
        this.dataNoticia = dataNoticia;
        this.imgNoticia = imgNoticia;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getImgCategoria() {
        return imgCategoria;
    }

    public void setImgCategoria(int imgCategoria) {
        this.imgCategoria = imgCategoria;
    }

    public String getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(String idNoticia) {
        this.idNoticia = idNoticia;
    }

    public String getTituloNoticia() {
        return tituloNoticia;
    }

    public void setTituloNoticia(String tituloNoticia) {
        this.tituloNoticia = tituloNoticia;
    }

    public String getDescricaoNoticia() {
        return descricaoNoticia;
    }

    public void setDescricaoNoticia(String descricaoNoticia) {
        this.descricaoNoticia = descricaoNoticia;
    }

    public String getDataNoticia() {
        return dataNoticia;
    }

    public void setDataNoticia(String dataNoticia) {
        this.dataNoticia = dataNoticia;
    }

    public int getImgNoticia() {
        return imgNoticia;
    }

    public void setImgNoticia(int imgNoticia) {
        this.imgNoticia = imgNoticia;
    }
}
