package com.fabianolira.appmiguelasnews.api;

import com.fabianolira.appmiguelasnews.model.Fotos;
import com.fabianolira.appmiguelasnews.model.Postagem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("/photos")
    Call<List<Fotos>> recuperarFotos();

    @GET("/posts")
    Call<List<Postagem>> recuperarPostagens();

}
