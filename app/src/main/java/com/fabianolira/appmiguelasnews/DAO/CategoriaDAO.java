package com.fabianolira.appmiguelasnews.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fabianolira.appmiguelasnews.helper.DbHelper;
import com.fabianolira.appmiguelasnews.model.Categoria;
import com.fabianolira.appmiguelasnews.model.Noticia;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements ICategoriaDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public CategoriaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Categoria categoria) {
        ContentValues cv = new ContentValues();

        cv.put("id_categoria", categoria.getId());
        cv.put("nome", categoria.getNome());
        cv.put("cor", categoria.getCor());


        try{
            escreve.insert(DbHelper.TABELA_CATEGORIAS, null, cv);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Categoria categoria) {
        return false;
    }

    @Override
    public boolean deletar(Categoria categoria) {
        return false;
    }


    public List<Categoria> listar() {

       List<Categoria> categoriaList = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_CATEGORIAS + ";";

        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()) {

            Categoria categoriaObj = new Categoria();

            String id_categoria = c.getString(c.getColumnIndex("id_categoria"));
            String nome = c.getString(c.getColumnIndex("nome"));
            String cor = c.getString(c.getColumnIndex("cor"));

            categoriaObj.setId(id_categoria);
            categoriaObj.setNome(nome);
            categoriaObj.setCor(cor);

            categoriaList.add(categoriaObj);

        }
        c.close();
        return categoriaList;
    }
}
