package com.fabianolira.appmiguelasnews.model;

public class Noticia {


    private String id;
    private String id_categoria;
    private String titulo;
    private String corpo;
    private String fonte_nm;
    private String fonte_url;
    private String dt_publicacao;
    private String status;
    private String image_noticia;


    public Noticia() {
    }

    public Noticia(String titulo_noticia, String autor_noticia, String data_noticia, String imagem_noticia) {
        this.titulo = titulo_noticia;
        this.fonte_nm = autor_noticia;
        this.dt_publicacao = data_noticia;
        this.image_noticia = imagem_noticia;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getFonte_nm() {
        return fonte_nm;
    }

    public void setFonte_nm(String fonte_nm) {
        this.fonte_nm = fonte_nm;
    }

    public String getFonte_url() {
        return fonte_url;
    }

    public void setFonte_url(String fonte_url) {
        this.fonte_url = fonte_url;
    }

    public String getDt_publicacao() {
        return dt_publicacao;
    }

    public void setDt_publicacao(String dt_publicacao) {
        this.dt_publicacao = dt_publicacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage_noticia() {
        return image_noticia;
    }

    public void setImage_noticia(String image_noticia) {
        this.image_noticia = image_noticia;
    }
}
