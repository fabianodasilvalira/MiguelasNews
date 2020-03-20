package com.fabianolira.appmiguelasnews.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fabianolira.appmiguelasnews.helper.DbHelper;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;

import java.util.ArrayList;
import java.util.List;

public class NoticiaDAO implements INoticiaDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public NoticiaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Noticia noticia) {
        ContentValues cv = new ContentValues();
        cv.put("id", noticia.getId());
        cv.put("titulo", noticia.getTitulo());
        cv.put("corpo", noticia.getCorpo());
        cv.put("dt_publicacao", noticia.getDt_publicacao());
        cv.put("fonte_nm", noticia.getFonte_nm());
        cv.put("fonte_url", noticia.getFonte_url());
        //cv.put("imagen_capa", noticia.getImagem_capa());

        try{
            escreve.insert(DbHelper.TABELA_NOTICIAS, null, cv);
            Log.i("Testando", "Sucesso ao salvar:  "
                    + noticia.getId() + "  -  "
                    + noticia.getFonte_url());
        }catch (Exception e){
            Log.i("Testando", "Erro ao salvar: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Noticia noticia) {
        return false;
    }

    @Override
    public boolean deletar(Noticia noticia) {
        return false;
    }

    @Override
    public List<Noticia> listar() {

       List<Noticia> noticiasList = new ArrayList<>();

       String sql = "SELECT * FROM " + DbHelper.TABELA_NOTICIAS + " ;";
        Log.i("Testando", "-->: " + sql);
       Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){

            Noticia noticiaObj = new Noticia();


            String id = c.getString(c.getColumnIndex("id"));
            String titulo = c.getString(c.getColumnIndex("titulo"));
           // String corpo = c.getString(c.getColumnIndex("corpo"));
            String dt_publicacao = c.getString(c.getColumnIndex("dt_publicacao"));
            String fonte_nm = c.getString(c.getColumnIndex("fonte_nm"));
            //String imagem_capa = c.getString(c.getColumnIndex("imagem_capa"));

            noticiaObj.setId(id);
            noticiaObj.setTitulo(titulo);
            //noticiaObj.setCorpo(corpo);
            noticiaObj.setDt_publicacao(dt_publicacao);
            noticiaObj.setFonte_nm(fonte_nm);
            //noticiaObj.setImagem_capa(imagem_capa);

            noticiasList.add(noticiaObj);

        }

        return noticiasList;
    }

    @Override
    public List<Noticia> listarNoticia() {

        List<Noticia> noticiasList = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_NOTICIAS + " WHERE id = " + Config.ID_NOTICIA + " ;" ;

        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){

            Noticia noticiaObj = new Noticia();

            String id = c.getString(c.getColumnIndex("id"));
            String titulo = c.getString(c.getColumnIndex("titulo"));
            String corpo = c.getString(c.getColumnIndex("corpo"));
            String dt_publicacao = c.getString(c.getColumnIndex("dt_publicacao"));
            String fonte_url = c.getString(c.getColumnIndex("fonte_url"));
            String fonte_nm = c.getString(c.getColumnIndex("fonte_nm"));
            //String imagem_capa = c.getString(c.getColumnIndex("imagem_capa"));
            Log.i("Testando saiu while", "->>: "+  id
                    +"\n"+ id
                    +"\n"+ titulo

                    +"\n"+ dt_publicacao
                    +"\n"+ fonte_url
                    +"\n"+ fonte_nm);

            noticiaObj.setId(String.valueOf(id));
            noticiaObj.setTitulo(titulo);
            noticiaObj.setCorpo(corpo);
            noticiaObj.setDt_publicacao(dt_publicacao);
            noticiaObj.setFonte_url(fonte_url);
            noticiaObj.setFonte_nm(fonte_nm);
            //noticiaObj.setImagem_capa(imagem_capa);

            noticiasList.add(noticiaObj);



            Log.i("INFORMA LISTAR LISTA", "->>: "+ " Id Noticia  "+ noticiasList);



        }

        return noticiasList;
    }

}
