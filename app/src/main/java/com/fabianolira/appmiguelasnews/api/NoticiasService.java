package com.fabianolira.appmiguelasnews.api;

import com.fabianolira.appmiguelasnews.model.Categoria;
import com.fabianolira.appmiguelasnews.model.Imagens;
import com.fabianolira.appmiguelasnews.model.Noticia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NoticiasService {

    @GET("api/noticia")
    Call<List<Noticia>> recuperarNoticia();


}
