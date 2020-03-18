package com.fabianolira.appmiguelasnews.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_MIGUELASNEWS";
    public static String TABELA_NOTICIAS = "noticias";

    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlNoticia = "CREATE TABLE IF NOT EXISTS " + TABELA_NOTICIAS
                             + " (id_sqlite INTEGER PRIMARY KEY AUTOINCREMENT,"
                             + " id TEXT NOT NULL,  "
                             + " titulo TEXT NOT NULL,  "
                             + " corpo TEXT NOT NULL, "
                             + " dt_publicacao TEXT NOT NULL, "
                             + " fonte_nm TEXT NOT NULL ) ";

        try{

            db.execSQL(sqlNoticia);
            Log.i("INFORMACAO CRIA BD", "Sucesso ao criar a tabela: " + sqlNoticia);
        }catch (Exception e){
            //Log.i("Info Db", "Erro ao criar a tabela: " + e.getMessage());
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
