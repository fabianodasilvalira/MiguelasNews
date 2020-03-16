package com.fabianolira.appmiguelasnews.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fabianolira.appmiguelasnews.helper.DbHelper;
import com.fabianolira.appmiguelasnews.model.Noticia;

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
        cv.put("imagen_capa", noticia.getImagem_capa());

        try{
            escreve.insert(DbHelper.TABELA_NOTICIAS, null, cv);
           /* Log.i("INFO teste", "Sucesso ao salvar:  "
                    + noticia.getId()
                    + noticia.getTitulo()
                   // + noticia.getImagem_capa()
                    + noticia.getDt_publicacao()
                    + noticia.getFonte_nm());*/
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar: " + e.getMessage());
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
       Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){
            //Log.i("INFO LISTAR2", "listar: " + sql);

            Noticia noticiaObj = new Noticia();

            String id = c.getString(c.getColumnIndex("id"));
            String titulo = c.getString(c.getColumnIndex("titulo"));
            String corpo = c.getString(c.getColumnIndex("corpo"));
            String dt_publicacao = c.getString(c.getColumnIndex("dt_publicacao"));
            String fonte_nm = c.getString(c.getColumnIndex("fonte_nm"));
            //String imagem_capa = c.getString(c.getColumnIndex("imagem_capa"));

            noticiaObj.setId(id);
            noticiaObj.setTitulo(titulo);
            noticiaObj.setCorpo(corpo);
            noticiaObj.setDt_publicacao(dt_publicacao);
            noticiaObj.setFonte_nm(fonte_nm);
            //noticiaObj.setImagem_capa(imagem_capa);
            Log.i("INFO LISTAR", "->>: "
                    + id + " / "
                    + corpo );

            noticiasList.add(noticiaObj);

            for (int i = 0; i < noticiasList.size(); i++) {

                Log.i("INFO LISTAR LISTA", "->>: "+noticiasList.get(i).getDt_publicacao());

            }

        }

        return noticiasList;
    }
}
