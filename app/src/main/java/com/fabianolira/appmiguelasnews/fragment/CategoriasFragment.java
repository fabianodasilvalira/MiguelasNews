package com.fabianolira.appmiguelasnews.fragment;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

import com.fabianolira.appmiguelasnews.DAO.CategoriaDAO;
import com.fabianolira.appmiguelasnews.DAO.NoticiaDAO;
import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.adapter.CategoriasAdapter;
import com.fabianolira.appmiguelasnews.adapter.NoticiasAdapter;
import com.fabianolira.appmiguelasnews.api.CategoriaService;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Categoria;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoriasFragment extends Fragment {
    private RecyclerView recyclerViewCategoria;
    private CategoriasAdapter adapter;
    private List<Categoria> listaCategoria = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private AlertDialog dialog;
    private Categoria categoria;
    private Retrofit retrofit;

    int tamTexto = 0;
    ArrayList<String> array_id_categoria, array_nome_categoria, array_cor_categoria, array_img_categoria;
    String[] str_id_categoria, str_nome_categoria, str_cor_categoria, str_img_categoria;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categorias, container, false);
        setHasOptionsMenu(true);

        recyclerViewCategoria = v.findViewById(R.id.recyclerViewCategoria);
        swipeRefreshLayout = v.findViewById(R.id.swipe);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewCategoria.setLayoutManager(layoutManager);
        recyclerViewCategoria.setHasFixedSize(true);

        array_id_categoria = new ArrayList<String>();
        array_nome_categoria = new ArrayList<String>();
        array_cor_categoria = new ArrayList<String>();
        array_img_categoria = new ArrayList<String>();

        str_id_categoria = new String[array_id_categoria.size()];
        str_nome_categoria = new String[array_nome_categoria.size()];
        str_cor_categoria = new String[array_cor_categoria.size()];
        str_img_categoria = new String[array_img_categoria.size()];

        retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_SERVIDOR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        if (JsonUtils.estaconectado(getContext())) {

            carregarCategoriasOnline();

        } else {

            carregarCategoriasOfline();

        }

        return v;
    }

    public void carregarCategoriasOfline() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        limpa();
                        if (JsonUtils.estaconectado(getContext())) {
                            carregarCategoriasOnline();
                        } else {
                            carregarCategoriasOfline();
                        }

                        // new NoticiaTask().execute(Config.URL_SERVIDOR + "api/noticia");
                    }
                }, 2000);
            }
        });


        CategoriaDAO categoriaDAO = new CategoriaDAO(getActivity());

        listaCategoria = categoriaDAO.listar();

        for (int i = 0; i > listaCategoria.size(); i++) {
            Log.i("LISTAGEM", "-->> " + listaCategoria.get(i));

        }

        adapter = new CategoriasAdapter(listaCategoria, getActivity());
        recyclerViewCategoria.setAdapter(adapter);


    }

    public void carregarCategoriasOnline() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        limpa();
                        if (JsonUtils.estaconectado(getContext())) {
                            carregarCategoriasOnline();
                        } else {
                            carregarCategoriasOfline();
                        }

                    }
                }, 2000);
            }
        });


        CategoriaService service = retrofit.create(CategoriaService.class);
        Call<List<Categoria>> call = service.recuperarCategoria();

        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()) {
                    listaCategoria = response.body();
                    Categoria categoria = new Categoria();

                    CategoriaDAO categoriaDAO = new CategoriaDAO(getContext());

                    for (Categoria news : listaCategoria) {
                        categoria = news;

                        categoriaDAO.salvar(categoria);

                    }

                }


                for (int j = 0; j < listaCategoria.size(); j++) {
                    categoria = listaCategoria.get(j);

                    array_id_categoria.add(categoria.getId());
                    str_id_categoria = array_id_categoria.toArray(str_id_categoria);

                    array_nome_categoria.add(categoria.getNome());
                    str_nome_categoria = array_nome_categoria.toArray(str_nome_categoria);

                    array_cor_categoria.add(categoria.getCor());
                    str_cor_categoria = array_cor_categoria.toArray(str_cor_categoria);

                    array_img_categoria.add(categoria.getImagem());
                    str_img_categoria = array_img_categoria.toArray(str_img_categoria);

                }


                adapter = new CategoriasAdapter(listaCategoria, getActivity());
                recyclerViewCategoria.setAdapter(adapter);
                //dialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

            }
        });
    }

    public void limpa() {
        int tamanho = this.listaCategoria.size();
        if (tamanho > 0) {
            for (int i = 0; i < tamanho; i++) {
                this.listaCategoria.remove(0);
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

                listaCategoria.clear();

                for (int i = 0; i < str_nome_categoria.length; i++) {
                    if (tamTexto <= str_nome_categoria[i].length()) {
                        if (str_nome_categoria[i].toLowerCase().contains(newText.toLowerCase())) {
                            Categoria cat = new Categoria();
                            cat.setId(str_id_categoria[i]);
                            cat.setNome(str_nome_categoria[i]);
                            cat.setCor(str_cor_categoria[i]);
                            cat.setImagem(str_img_categoria[i]);
                            listaCategoria.add(cat);
                        }
                    }
                }
                adapter = new CategoriasAdapter(listaCategoria, getActivity());
                recyclerViewCategoria.setAdapter(adapter);
                return false;
            }
        });
    }
}
