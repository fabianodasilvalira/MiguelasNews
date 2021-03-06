package com.fabianolira.appmiguelasnews.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fabianolira.appmiguelasnews.helper.DbHelper;
import com.fabianolira.appmiguelasnews.model.Categoria;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;

import java.util.ArrayList;
import java.util.List;

public class NoticiaDAO implements INoticiaDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private int qtdNoticiasOfline;

    public NoticiaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Noticia noticia) {

        if(qtdNoticiasOfline == 0){
            qtdNoticiasOfline++;
            deletar(noticia);
        }

        qtdNoticiasOfline++;
        ContentValues cv = new ContentValues();

        cv.put("id_noticia", noticia.getId());
        cv.put("titulo", noticia.getTitulo());
        cv.put("corpo", noticia.getCorpo());
        cv.put("nome_categoria", noticia.getCategoria().getNome());
        cv.put("cor_categoria", noticia.getCategoria().getCor());
        cv.put("dt_publicacao", noticia.getDt_publicacao());
        cv.put("fonte_nm", noticia.getFonte_nm());
        cv.put("fonte_url", noticia.getFonte_url());
        //cv.put("imagen_capa", noticia.getImagem_capa());

        try {

            escreve.insert(DbHelper.TABELA_NOTICIAS, null, cv);

        } catch (Exception e) {
            return false;
        }

        Config.QTDNOTICIASOFLINE = qtdNoticiasOfline;
        return true;


    }

    @Override
    public boolean atualizar(Noticia noticia) {
        return false;
    }

    @Override
    public boolean deletar(Noticia noticia) {
        try{
            escreve.delete(DbHelper.TABELA_NOTICIAS, null, null);
        } catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public List<Noticia> listar() {

        List<Noticia> noticiasList = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_NOTICIAS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()) {

            Noticia noticiaObj = new Noticia();
            Categoria categoria = new Categoria();



            String id_noticia = c.getString(c.getColumnIndex("id_noticia"));
            String titulo = c.getString(c.getColumnIndex("titulo"));
            String nome_categoria = c.getString(c.getColumnIndex("nome_categoria"));
            String cor_categoria = c.getString(c.getColumnIndex("cor_categoria"));
            String dt_publicacao = c.getString(c.getColumnIndex("dt_publicacao"));
            String fonte_nm = c.getString(c.getColumnIndex("fonte_nm"));
            //String imagem_capa = c.getString(c.getColumnIndex("imagem_capa"));

            noticiaObj.setId(id_noticia);
            noticiaObj.setTitulo(titulo);

            categoria.setNome(nome_categoria);
            categoria.setCor(cor_categoria);

            noticiaObj.setCategoria(categoria);

            noticiaObj.setDt_publicacao(dt_publicacao);
            noticiaObj.setFonte_nm(fonte_nm);
            //noticiaObj.setImagem_capa(imagem_capa);

            noticiasList.add(noticiaObj);

        }
        c.close();
        return noticiasList;
    }

    @Override
    public List<Noticia> listarNoticia() {

        List<Noticia> noticiasList = new ArrayList<>();

        //String[] params = {Config.ID_NOTICIA.toString()};
        //escreve.delete(DbHelper.TABELA_NOTICIAS, "id = ?", params);

        String sql = "SELECT * FROM " + DbHelper.TABELA_NOTICIAS + " WHERE id_noticia = " + Config.ID_NOTICIA + ";";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()) {

            Noticia noticiaObj = new Noticia();

            String id_noticia = c.getString(c.getColumnIndex("id_noticia"));
            String titulo = c.getString(c.getColumnIndex("titulo"));
            String corpo = c.getString(c.getColumnIndex("corpo"));
            String dt_publicacao = c.getString(c.getColumnIndex("dt_publicacao"));
            String fonte_url = c.getString(c.getColumnIndex("fonte_url"));
            String fonte_nm = c.getString(c.getColumnIndex("fonte_nm"));
            //String imagem_capa = c.getString(c.getColumnIndex("imagem_capa"));


            noticiaObj.setId(String.valueOf(id_noticia));
            noticiaObj.setTitulo(titulo);
            noticiaObj.setCorpo(corpo);
            noticiaObj.setDt_publicacao(dt_publicacao);
            noticiaObj.setFonte_url(fonte_url);
            noticiaObj.setFonte_nm(fonte_nm);
            //noticiaObj.setImagem_capa(imagem_capa);

            noticiasList.add(noticiaObj);

        }
        c.close();
        return noticiasList;
    }

}
