package com.fabianolira.appmiguelasnews.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Noticia implements Serializable {

    private String id;
    private String id_categoria;
    private String titulo;
    private String corpo;
    private String fonte_nm;
    private String fonte_url;
    private String dt_publicacao;
    private String status;
    private String imagem_capa;
    private List<Imagens> imagens;


    public Noticia() {
    }

    public Noticia(String titulo, String autor, String data, List<Imagens> imagens) {
        this.titulo = titulo;
        this.fonte_nm = autor;
        this.dt_publicacao = data;
        this.imagens = imagens;
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

    public String getImagen_capa() {
        return imagem_capa;
    }

    public void setImagen_capa(String imagen_capa) {
        this.imagem_capa = imagen_capa;
    }

    public List<Imagens> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagens> imagens) {
        this.imagens = imagens;
    }
}

