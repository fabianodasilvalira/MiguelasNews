package com.fabianolira.appmiguelasnews.fragment;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fabianolira.appmiguelasnews.DAO.NoticiaDAO;
import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.adapter.NoticiasAdapter;
import com.fabianolira.appmiguelasnews.api.CategoriaService;
import com.fabianolira.appmiguelasnews.api.NoticiasService;
import com.fabianolira.appmiguelasnews.helper.DbHelper;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Categoria;
import com.fabianolira.appmiguelasnews.model.Imagens;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasRecentesFragment extends Fragment {

    RecyclerView recyclerViewNoticias;
    private NoticiasAdapter adapter;
    private List<Noticia> listaNoticia = new ArrayList<>();
    private List<Noticia> listaNoticia2 = new ArrayList<>();
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout = null;
    private AlertDialog dialog;
    private Noticia noticia;
    private Retrofit retrofit;
    private Imagens imagens;

    int tamTexto = 0;
    ArrayList<String> array_noticia, array_id_noticia, array_id_categoria, array_titulo, array_imagem, array_autor, array_data, array_ativo;
    String[] str_noticia, str_id_noticia, str_id_categoria, str_titulo, str_imagem, str_autor, str_data, str_ativo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_noticias_recentes, container, false);

        setHasOptionsMenu(true);
        swipeRefreshLayout = v.findViewById(R.id.swipe);

        recyclerViewNoticias = v.findViewById(R.id.recyclerViewNoticias);
        progressBar = v.findViewById(R.id.progressBar);


        //configurar recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNoticias.setLayoutManager(layoutManager);
        recyclerViewNoticias.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNoticias.setHasFixedSize(true);

        listaNoticia = new ArrayList<Noticia>();

        array_noticia = new ArrayList<String>();
        array_id_noticia = new ArrayList<String>();
        array_id_categoria = new ArrayList<String>();
        array_titulo = new ArrayList<String>();
        array_imagem = new ArrayList<String>();
        array_autor = new ArrayList<String>();
        array_data = new ArrayList<String>();
        array_ativo = new ArrayList<String>();

        str_noticia = new String[array_noticia.size()];
        str_id_noticia = new String[array_id_noticia.size()];
        str_id_categoria = new String[array_id_categoria.size()];
        str_titulo = new String[array_titulo.size()];
        str_imagem = new String[array_imagem.size()];
        str_autor = new String[array_autor.size()];
        str_data = new String[array_data.size()];
        str_ativo = new String[array_ativo.size()];


        retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_SERVIDOR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if (JsonUtils.estaconectado(getContext())) {
            Toast.makeText(getContext(), "Conectado a internet", Toast.LENGTH_SHORT).show();


            carregarNoticiasOnline();

            //new NoticiaTask().execute(Config.URL_SERVIDOR + "api/noticia");
            //Log.d("noticias", "mostrarJson: " + Config.URL_SERVIDOR + "api/noticia");


        } else {

            carregarNoticiasOfline();

        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        limpa();
                        if (JsonUtils.estaconectado(getContext())) {
                            carregarNoticiasOnline();
                        } else {
                            carregarNoticiasOfline();
                        }
                        // new NoticiaTask().execute(Config.URL_SERVIDOR + "api/noticia");
                    }
                }, 2000);
            }
        });

        return v;
    }

    public void carregarNoticiasOfline() {
        dialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setMessage("Carregando noticias, Aguarde!")
                .setCancelable(false)
                .build();
        dialog.show();

        NoticiaDAO noticiaDAO = new NoticiaDAO(getActivity());

        listaNoticia2 = noticiaDAO.listar();

        for (int i = 0; i > listaNoticia2.size(); i++) {
            Log.i("LISTAGEM", "-->> " + listaNoticia2.get(i));

        }

        //Collections.reverse(listaNoticia2);
        adapter = new NoticiasAdapter(getActivity(), listaNoticia2);
        recyclerViewNoticias.setAdapter(adapter);

        dialog.dismiss();

        Log.d("Sem conexao", "onCreateView: ");
        Toast.makeText(getContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        limpa();
                        if (JsonUtils.estaconectado(getContext())) {
                            carregarNoticiasOnline();
                        } else {
                            carregarNoticiasOfline();
                        }
                        // new NoticiaTask().execute(Config.URL_SERVIDOR + "api/noticia");
                    }
                }, 2000);
            }
        });
    }


    public void carregarNoticiasOnline() {

        dialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setMessage("Carregando noticias, Aguarde!")
                .setCancelable(false)
                .build();
        dialog.show();

        NoticiasService service = retrofit.create(NoticiasService.class);
        Call<List<Noticia>> call = service.recuperarNoticia();


        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                if (response.isSuccessful()) {
                    listaNoticia = response.body();
                    Noticia noticia = new Noticia();
                    //Log.d("Imagem capa", "onResponse: " + noticia.getImagen_capa());




                    NoticiaDAO noticiaDAO = new NoticiaDAO(getContext());


                    for (Noticia news : listaNoticia) {
                        noticia = news;

                        noticiaDAO.salvar(noticia);
                    }

                }


                for (int j = 0; j < listaNoticia.size(); j++) {
                    noticia = listaNoticia.get(j);

                    array_noticia.add(noticia.getCorpo());
                    str_noticia = array_noticia.toArray(str_noticia);

                    array_id_noticia.add(String.valueOf(noticia.getId()));
                    str_id_noticia = array_id_noticia.toArray(str_id_noticia);

                    array_id_categoria.add(noticia.getId_categoria());
                    str_id_categoria = array_id_categoria.toArray(str_id_categoria);

                    array_titulo.add(noticia.getTitulo());
                    str_titulo = array_titulo.toArray(str_titulo);

                    array_imagem.add(noticia.getImagen_capa());
                    str_imagem = array_imagem.toArray(str_imagem);

                    array_autor.add(noticia.getFonte_nm());
                    str_autor = array_autor.toArray(str_autor);

                    array_data.add(noticia.getDt_publicacao());
                    str_data = array_data.toArray(str_data);

                    array_ativo.add(String.valueOf(noticia.getStatus()));
                    str_ativo = array_ativo.toArray(str_ativo);
                }

                //Collections.reverse(listaNoticia);
                adapter = new NoticiasAdapter(getActivity(), listaNoticia);
                recyclerViewNoticias.setAdapter(adapter);
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                Toast.makeText(getContext(), "Erro ao se conectar com o servidor.", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                //Log.d("entrou no erro", "resultado: " + t.getLocalizedMessage());
            }
        });
    }


    public void limpa() {
        int tamanho = this.listaNoticia.size();
        if (tamanho > 0) {
            for (int i = 0; i < tamanho; i++) {
                this.listaNoticia.remove(0);

            }
            adapter.notifyItemRangeRemoved(0, tamanho);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        final MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("", false);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tamTexto = newText.length();

                listaNoticia.clear();


                for (int i = 0; i < str_titulo.length; i++) {
                    if (str_titulo[i].toLowerCase().contains(newText.toLowerCase())) {
                        Noticia objItem = new Noticia();

                        objItem.setId(str_id_noticia[i]);
                        objItem.setId_categoria(str_id_categoria[i]);
                        objItem.setTitulo(str_titulo[i]);
                        objItem.setCorpo(str_noticia[i]);
                        objItem.setDt_publicacao(str_data[i]);
                        objItem.setFonte_nm(str_autor[i]);
                        objItem.setImagen_capa(str_imagem[i]);
                        objItem.setStatus(str_ativo[i]);
                        listaNoticia.add(objItem);


                    }
                }
                Collections.reverse(listaNoticia);
                adapter = new NoticiasAdapter(getActivity(), listaNoticia);
                recyclerViewNoticias.setAdapter(adapter);
                return false;
            }
        });

    }
}