package com.fabianolira.appmiguelasnews.fragment;


import android.app.AlertDialog;
import android.app.StatusBarManager;
import android.os.AsyncTask;
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

import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.adapter.CategoriasAdapter;
import com.fabianolira.appmiguelasnews.adapter.NoticiasAdapter;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Categoria;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class CategoriasFragment extends Fragment {
    private RecyclerView recyclerViewCategoria;
    private CategoriasAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Categoria> listaCategoria = new ArrayList<>();
    private ProgressBar progressBar;
    private AlertDialog dialog;
    private Categoria categoria;

    int tamTexto = 0;
    ArrayList<String> array_id_categoria, array_nome_categoria, array_img_categoria;
    String[] str_id_categoria, str_nome_categoria, str_img_categoria;


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

        array_id_categoria   = new ArrayList<String>();
        array_nome_categoria = new ArrayList<String>();
        array_img_categoria  = new ArrayList<String>();

        str_id_categoria   = new String[array_id_categoria.size()];
        str_nome_categoria = new String[array_nome_categoria.size()];
        str_img_categoria  = new String[array_img_categoria.size()];


        if (JsonUtils.estaconectado(getContext())) {
         /*   dialog = new SpotsDialog.Builder()
                    .setContext(getContext())
                    .setMessage("Carregando categorias!")
                    .setCancelable(false)
                    .build();
            dialog.show();*/
            new CategoriaTask().execute(Config.URL_SERVIDOR + "api/categoria");

        } else {
            Toast.makeText(getContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        limpa();

                        new CategoriaTask().execute(Config.URL_SERVIDOR + "api/categoria");
                    }
                }, 2000);
            }
        });

        return v;
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

    public class CategoriaTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return JsonUtils.retornaJsonDeGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            try {
                JSONArray raiz = new JSONArray(json);
                String nome = "";
                for (int i = 0; i < raiz.length(); i++) {

                    JSONObject obj = raiz.getJSONObject(i);

                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(String.valueOf(obj.getInt("id_categoria")));
                    categoria.setNomeCategoria(obj.getString("descricao_categoria"));
                    categoria.setImgCategoria(obj.getString("imagem_categoria"));

                    listaCategoria.add(categoria);
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

            for (int j = 0; j<listaCategoria.size(); j++){
                categoria = listaCategoria.get(j);
                array_id_categoria.add(categoria.getIdCategoria());
                str_id_categoria = array_id_categoria.toArray(str_id_categoria);

                array_nome_categoria.add(categoria.getNomeCategoria());
                str_nome_categoria = array_nome_categoria.toArray(str_nome_categoria);

                array_img_categoria.add(categoria.getImgCategoria());
                str_img_categoria = array_img_categoria.toArray(str_img_categoria);

            }

            adapter = new CategoriasAdapter(listaCategoria, getActivity());
            recyclerViewCategoria.setAdapter(adapter);
            //dialog.dismiss();
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

                for (int i=0;i<str_nome_categoria.length;i++){
                    if(tamTexto<=str_nome_categoria[i].length()){
                        if(str_nome_categoria[i].toLowerCase().contains(newText.toLowerCase())){
                            Categoria cat = new Categoria();
                            cat.setIdCategoria(str_id_categoria[i]);
                            cat.setNomeCategoria(str_nome_categoria[i]);
                            cat.setImgCategoria(str_img_categoria[i]);
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
