package com.fabianolira.appmiguelasnews.model;

public class Noticia {


    private int id_noticias;
    //private String id_categoria;
    private String titulo_noticia;
    private String descricao_noticia;
    private String autor_noticia;
    private String data_noticia;
    private String imagem_noticia;
    private int ativo;

    public Noticia() {
    }

    public Noticia(String titulo_noticia, String autor_noticia, String data_noticia, String imagem_noticia) {
        this.titulo_noticia = titulo_noticia;
        this.autor_noticia = autor_noticia;
        this.data_noticia = data_noticia;
        this.imagem_noticia = imagem_noticia;
    }

    public int getId_noticias() {
        return id_noticias;
    }

    public void setId_noticias(int id_noticias) {
        this.id_noticias = id_noticias;
    }

    public String getTitulo_noticia() {
        return titulo_noticia;
    }

    public void setTitulo_noticia(String titulo_noticia) {
        this.titulo_noticia = titulo_noticia;
    }

    public String getDescricao_noticia() {
        return descricao_noticia;
    }

    public void setDescricao_noticia(String descricao_noticia) {
        this.descricao_noticia = descricao_noticia;
    }

    public String getAutor_noticia() {
        return autor_noticia;
    }

    public void setAutor_noticia(String autor_noticia) {
        this.autor_noticia = autor_noticia;
    }

    public String getData_noticia() {
        return data_noticia;
    }

    public void setData_noticia(String data_noticia) {
        this.data_noticia = data_noticia;
    }

    public String getImagem_noticia() {
        return imagem_noticia;
    }

    public void setImagem_noticia(String imagem_noticia) {
        this.imagem_noticia = imagem_noticia;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }
}
