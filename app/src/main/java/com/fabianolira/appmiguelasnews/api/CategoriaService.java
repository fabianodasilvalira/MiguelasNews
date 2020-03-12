package com.fabianolira.appmiguelasnews.api;

import com.fabianolira.appmiguelasnews.model.Categoria;
import com.fabianolira.appmiguelasnews.model.Noticia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface CategoriaService {

    @GET("api/categoria")
    Call<List<Categoria>> recuperarCategoria();

    @GET("api/por-categoria?id={id}")
    Call<Noticia> recuperarCategoriaId(@Path("id") String id);

}
