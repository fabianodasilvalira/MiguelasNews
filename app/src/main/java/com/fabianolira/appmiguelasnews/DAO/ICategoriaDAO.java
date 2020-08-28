package com.fabianolira.appmiguelasnews.DAO;

import com.fabianolira.appmiguelasnews.model.Categoria;

import java.util.List;

public interface ICategoriaDAO {
    boolean salvar(Categoria categoria);
    boolean atualizar(Categoria categoria);
    boolean deletar(Categoria categoria);
    List<Categoria> listar();
}
