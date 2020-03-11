package com.fabianolira.appmiguelasnews.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.adapter.NoticiasAdapter;
import com.fabianolira.appmiguelasnews.adapter.NoticiasPorCategoriaAdapter;
import com.fabianolira.appmiguelasnews.fragment.NoticiasRecentesFragment;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class CategoriaDetalhesActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNoticias;
    private NoticiasPorCategoriaAdapter  adapter;
    private List<Noticia> listaNoticia = new ArrayList<>();
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_detalhes);


        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerViewNoticias = findViewById(R.id.recyclerViewCategoriaDetalhes);
        progressBar = findViewById(R.id.progressBar);

        listaNoticia = new ArrayList<Noticia>();

        //configurar recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewNoticias.setLayoutManager(layoutManager);
        recyclerViewNoticias.setItemAnimator(new DefaultItemAnimator());

        final Toolbar toolbar = findViewById(R.id.toolbarCategoria);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(Config.TITULO_CATEGORIA);
        }


        if (JsonUtils.estaconectado(getApplicationContext())) {
            dialog = new SpotsDialog.Builder()
                    .setContext(this)
                    .setMessage("Carregando noticias por categoria!")
                    .setCancelable(false)
                    .build();
            dialog.show();
             new NoticiaTask().execute(Config.URL_SERVIDOR + "api/noticia/por-categoria?id=" + Config.ID_CATEGORIA);
            Log.d("categoria", "onCreate: " + Config.URL_SERVIDOR + "api/noticia/por-categoria?id=" + Config.ID_CATEGORIA);

        } else {
            Toast.makeText(getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        }

      swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        limpa();
                        new NoticiaTask().execute(Config.URL_SERVIDOR + "api/noticia/por-categoria?id=" + Config.ID_CATEGORIA);
                    }
                }, 1000);
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

    public class NoticiaTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return JsonUtils.retornaJsonDeGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            try {
                JSONArray raiz = new JSONArray(json);

                for (int i = 0; i < raiz.length(); i++) {

                    JSONObject obj = raiz.getJSONObject(i);

                    Noticia noticia = new Noticia();
                    noticia.setId(obj.getString("id"));
                    //noticia.setImagen_capa(obj.getString("imagen_capa"));
                    noticia.setTitulo(obj.getString("titulo"));
                    noticia.setCorpo(obj.getString("corpo"));
                    noticia.setFonte_nm(obj.getString("fonte_nm"));
                    noticia.setDt_publicacao(obj.getString("dt_publicacao"));

                    Log.d("ListaNoticia", "noticia: " + obj + "\n");

                    listaNoticia.add(noticia);


                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

            Collections.reverse(listaNoticia);
            adapter = new NoticiasPorCategoriaAdapter(getApplicationContext(), listaNoticia);
            recyclerViewNoticias.setAdapter(adapter);
            dialog.dismiss();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

}
