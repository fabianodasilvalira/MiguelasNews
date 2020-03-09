package com.fabianolira.appmiguelasnews.api;

import com.fabianolira.appmiguelasnews.model.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface CategoriaService {

    @GET("api/categoria")
    Call<List<Categoria>> recuperarCategoria();

}
