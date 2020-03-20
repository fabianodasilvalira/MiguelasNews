package com.fabianolira.appmiguelasnews.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Noticia implements Serializable {

    private String id;
    private int id_sqlite;
    private String id_categoria;
    private String titulo;
    private String corpo;
    private String fonte_nm;
    private String fonte_url;
    private String dt_publicacao;
    private String status;
    private String imagem_capa;
    private String imagem_corpo;
    private List<Imagens> imagens;
    private byte[] imagen_ofline;


    public Noticia() {
    }

    public Noticia(String titulo, String autor, String data, String imagem_capa) {
        this.titulo = titulo;
        this.fonte_nm = autor;
        this.dt_publicacao = data;
        this.imagem_capa = imagem_capa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getId_sqlite() {
        return id_sqlite;
    }

    public void setId_sqlite(int id_sqlite) {
        this.id_sqlite = id_sqlite;
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

    public String getImagem_capa() {
        return imagem_capa;
    }

    public void setImagem_capa(String imagem_capa) {
        this.imagem_capa = imagem_capa;
    }

    public String getImagem_corpo() {
        return imagem_corpo;
    }

    public void setImagem_corpo(String imagem_corpo) {
        this.imagem_corpo = imagem_corpo;
    }

    public byte[] getImagen_ofline() {
        return imagen_ofline;
    }

    public void setImagen_ofline(byte[] imagen_ofline) {
        this.imagen_ofline = imagen_ofline;
    }
}

