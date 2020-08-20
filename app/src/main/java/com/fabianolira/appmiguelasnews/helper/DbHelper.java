package com.fabianolira.appmiguelasnews.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "MIGUELASNEWS";
    public static String TABELA_NOTICIAS = "noticia";
    public static String TABELA_CATEGORIAS = "categoria";


    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlNoticia = "CREATE TABLE IF NOT EXISTS " + TABELA_NOTICIAS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " id_noticia TEXT NOT NULL,  "
                + " titulo TEXT NOT NULL,  "
                + " corpo TEXT NOT NULL, "
                + " nome_categoria TEXT NOT NULL, "
                + " cor_categoria TEXT NOT NULL, "
                + " dt_publicacao TEXT NULL, "
                + " fonte_url TEXT NULL, "
                + " fonte_nm TEXT NULL ) ";

        String sqlCategoria = "CREATE TABLE IF NOT EXISTS " + TABELA_CATEGORIAS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " id_categoria TEXT NOT NULL,  "
                + " nome TEXT NOT NULL,  "
                + " cor TEXT NOT NULL) ";

        try {


            db.execSQL(sqlNoticia);
            db.execSQL(sqlCategoria);

        } catch (Exception e) {
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
