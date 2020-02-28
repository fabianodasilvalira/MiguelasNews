package com.fabianolira.appmiguelasnews.api;

import com.fabianolira.appmiguelasnews.model.Fotos;
import com.fabianolira.appmiguelasnews.model.Noticia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NoticiasService {

        @GET("default")
        Call<List<Noticia>> recuperarNoticia();



    }

