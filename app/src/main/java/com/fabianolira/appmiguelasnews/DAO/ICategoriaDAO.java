package com.fabianolira.appmiguelasnews.DAO;

import com.fabianolira.appmiguelasnews.model.Categoria;

import java.util.List;

public interface ICategoriaDAO {
    public boolean salvar(Categoria categoria);
    public boolean atualizar(Categoria categoria);
    public boolean deletar(Categoria categoria);
    public List<Categoria> listar();
}
