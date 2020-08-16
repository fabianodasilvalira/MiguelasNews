package com.fabianolira.appmiguelasnews.api;

import com.fabianolira.appmiguelasnews.model.Categoria;
import com.fabianolira.appmiguelasnews.model.Imagens;
import com.fabianolira.appmiguelasnews.model.Noticia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NoticiasService {

    @GET("api/noticia")
    Call<List<Noticia>> recuperarNoticia();

    @GET("api/noticia/{id}")
    Call<Noticia> recuperarNoticiaId(@Path("id") String id);

    @GET("api/noticia/por-status/{status}")
    Call<List<Noticia>> recuperarNoticiaPorStatus(@Path("status") int status);

    @GET("api/noticia/por-categoria/{id}")
    Call<List<Noticia>> recuperarCategoriaId(@Path("id") String id);

}
