package com.fabianolira.appmiguelasnews.model;

import java.io.Serializable;

public class Categoria implements Serializable {

    private String id;
    private String nome;
    private String cor;
    private String id_user;
    private String imagem;
    private String dt_in;
    private String dt_up;


    public Categoria() {
    }

    public Categoria(String nome, String imagem) {
        this.nome = nome;
        this.imagem = imagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDt_in() {
        return dt_in;
    }

    public void setDt_in(String dt_in) {
        this.dt_in = dt_in;
    }

    public String getDt_up() {
        return dt_up;
    }

    public void setDt_up(String dt_up) {
        this.dt_up = dt_up;
    }
}
