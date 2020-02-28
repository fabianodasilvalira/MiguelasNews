package com.fabianolira.appmiguelasnews.fragment;


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

import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.adapter.NoticiasAdapter;
import com.fabianolira.appmiguelasnews.api.DataService;
import com.fabianolira.appmiguelasnews.api.NoticiasService;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Fotos;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_noticias_recentes, container, false);

        setHasOptionsMenu(true);
        swipeRefreshLayout = v.findViewById(R.id.swipe);

        recyclerViewNoticias = v.findViewById(R.id.recyclerViewNoticias);
        progressBar = v.findViewById(R.id.progressBar);

        

        //configurar adapter
        adapter = new NoticiasAdapter(getActivity(), listaNoticia);

        //configurar recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNoticias.setLayoutManager(layoutManager);
        recyclerViewNoticias.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNoticias.setHasFixedSize(true);


        recyclerViewNoticias.setAdapter(adapter);


        if(JsonUtils.estaconectado(getContext())){
            new NoticiaTask().execute(Config.URL_SERVIDOR + "api/noticia");
        }else{
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
                        new NoticiaTask().execute(Config.URL_SERVIDOR + "api/noticia");
                    }
                }, 2000);
            }
        });




        return v;
    }

    public void limpa(){
        int tamanho = this.listaNoticia.size();
        if(tamanho > 0){
            for (int i = 0; i < tamanho; i++){
                this.listaNoticia.remove(0);
            }
            adapter.notifyItemRangeRemoved(0, tamanho);
        }
    }

    public class NoticiaTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return JsonUtils.retornaJsonDeGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            try {
                JSONArray raiz = new JSONArray(json);
                String nome="";
                for(int i = 0; i < raiz.length(); i++ ){

                    JSONObject obj = raiz.getJSONObject(i);

                    Noticia noticia = new Noticia();
                    noticia.setId_noticias(obj.getInt("id_noticias"));
                    noticia.setImagem_noticia(obj.getString("image_noticia"));
                    noticia.setTitulo_noticia(obj.getString("titulo_noticia"));
                    noticia.setDescricao_noticia(obj.getString("descricao_noticia"));
                    noticia.setAutor_noticia(obj.getString("autor_noticia"));
                    noticia.setData_noticia(obj.getString("data_noticia"));

                    listaNoticia.add(noticia);


                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

            adapter = new NoticiasAdapter(getActivity(), listaNoticia);
            recyclerViewNoticias.setAdapter(adapter);
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
    }







}
