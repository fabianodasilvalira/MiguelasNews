package com.fabianolira.appmiguelasnews.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.adapter.NoticiasPorCategoriaAdapter;
import com.fabianolira.appmiguelasnews.api.NoticiasService;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoriaDetalhesActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNoticias;
    private NoticiasPorCategoriaAdapter adapter;
    private List<Noticia> listaNoticia = new ArrayList<>();
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private AlertDialog dialog;
    private Retrofit retrofit;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_detalhes);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


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
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(Config.TITULO_CATEGORIA);
        }


        if (JsonUtils.estaconectado(getApplicationContext())) {
            dialog = new SpotsDialog.Builder()
                    .setContext(this)
                    .setMessage("Carregando noticias por categoria, Aguarde!")
                    .setCancelable(false)
                    .build();
            dialog.show();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_SERVIDOR)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            carregarNoticias();

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
                        carregarNoticias();
                        //new NoticiaTask().execute(Config.URL_SERVIDOR + "api/noticia/por-categoria?id=" + Config.ID_CATEGORIA);
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

    public void carregarNoticias() {
        NoticiasService service = retrofit.create(NoticiasService.class);
        Call<List<Noticia>> call = service.recuperarCategoriaId(Config.ID_CATEGORIA);
        //Log.d("Entrou ", "onResponse: " + call.toString());

        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                if (response.isSuccessful()) {
                    //Log.d("Entrou capa", "onResponse: ");
                    listaNoticia = response.body();
                    Noticia noticia = new Noticia();
                    //Log.d("Imagem capa", "onResponse: " + noticia.getImagen_capa());

                }

                Collections.reverse(listaNoticia);
                adapter = new NoticiasPorCategoriaAdapter(getApplicationContext(), listaNoticia);
                recyclerViewNoticias.setAdapter(adapter);
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {

                //Log.d("entrou no erro", "resultado: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

}
