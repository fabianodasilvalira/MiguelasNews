package com.fabianolira.appmiguelasnews.DAO;

import com.fabianolira.appmiguelasnews.model.Noticia;

import java.util.List;

public interface INoticiaDAO {
    public boolean salvar(Noticia noticia);
    public boolean atualizar(Noticia noticia);
    public boolean deletar(Noticia noticia);
    public List<Noticia> listar();
}
