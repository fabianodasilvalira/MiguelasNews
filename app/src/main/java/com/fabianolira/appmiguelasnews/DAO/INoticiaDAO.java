package com.fabianolira.appmiguelasnews.DAO;

import com.fabianolira.appmiguelasnews.model.Noticia;

import java.util.List;

public interface INoticiaDAO {
    boolean salvar(Noticia noticia);
    boolean atualizar(Noticia noticia);
    boolean deletar(Noticia noticia);
    List<Noticia> listar();
    List<Noticia> listarNoticia();
}
