package com.fabianolira.appmiguelasnews.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fabianolira.appmiguelasnews.DAO.NoticiaDAO;
import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.api.NoticiasService;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoticiasDetalhesActivity extends AppCompatActivity {

    private ImageView imgPrincipal, imgCorpoNoticia;
    private TextView txtTitulo, txtData, txtAutor, webDescricao, urlNoticia;
    LinearLayout linearLayout;
    private RecyclerView recyclerImagens;

    private ActionBar actionBar;
    private AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private AlertDialog dialog;
    private TextView voltar;
    Retrofit retrofit;

    Noticia noticia;
    private AdView mAdView;
    //WebView webDescricao;
    List<Noticia> listaNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_detalhes);

        FirebaseMessaging.getInstance().subscribeToTopic("All");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        voltar = findViewById(R.id.voltar);

        /* ----- VÁRIAS IMAGENS --------- Não excluí*/
//        recyclerImagens = findViewById(R.id.recyclerViewImagensDetalhes);

        //Definir Layout
        /*RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerImagens.setLayoutManager(layoutManager);
        //definir adaptador
        ImagemAdapter adapter = new ImagemAdapter();
        recyclerImagens.setAdapter(adapter);*/


        final Toolbar toolbar = findViewById(R.id.toolbarNoticiasDetalhes);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean estaMostrando = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    if (JsonUtils.estaconectado(getApplicationContext())) {
                        collapsingToolbarLayout.setTitle(noticia.getTitulo());

                    } else {
                        collapsingToolbarLayout.setTitle("");

                    }
                    estaMostrando = true;

                } else {
                    collapsingToolbarLayout.setTitle("");
                    estaMostrando = false;
                }
            }
        });

        collapsingToolbarLayout = findViewById(R.id.ColalapsingToolbar);

        imgPrincipal = findViewById(R.id.imagemPrincipal);
        // imgFavorito = findViewById(R.id.)

        txtTitulo = findViewById(R.id.textTituloDetalhes);
        urlNoticia = findViewById(R.id.urlNoticia);
        txtAutor = findViewById(R.id.textAutor);
        txtData = findViewById(R.id.textDataDetalheso);
        webDescricao = findViewById(R.id.webDescricao);
        imgPrincipal = findViewById(R.id.imagemPrincipal);
        imgCorpoNoticia = findViewById(R.id.imagemDetalhe);
        listaNoticia = new ArrayList<Noticia>();

        retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_SERVIDOR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if (JsonUtils.estaconectado(getApplicationContext())) {
            dialog = new SpotsDialog.Builder()
                    .setContext(this)
                    .setMessage("Carregando a noticia, Aguarde!")
                    .setCancelable(false)
                    .build();
            dialog.show();

            carregarNoticias();

        } else {

            carregarNoticiaOfline();
            Toast.makeText(getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_LONG).show();
        }

    }

    public void carregarNoticiaOfline() {

        NoticiaDAO noticiaDAO = new NoticiaDAO(getApplicationContext());

        listaNoticia = noticiaDAO.listarNoticia();

        txtTitulo.setText(listaNoticia.get(0).getTitulo());
        urlNoticia.setText(listaNoticia.get(0).getFonte_url());

        String data = listaNoticia.get(0).getDt_publicacao();
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            txtData.setText(newFormat.format(oldFormat.parse(data)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        webDescricao.setText(listaNoticia.get(0).getCorpo());
        txtAutor.setText(listaNoticia.get(0).getFonte_nm());


    }

    public void carregarNoticias() {
        NoticiasService service = retrofit.create(NoticiasService.class);
        Call<Noticia> call = service.recuperarNoticiaId(Config.ID_NOTICIA);

        call.enqueue(new Callback<Noticia>() {
            @Override
            public void onResponse(Call<Noticia> call, Response<Noticia> response) {
                if (response.isSuccessful()) {
                    noticia = response.body();
                    populaDados();
                }
            }

            @Override
            public void onFailure(Call<Noticia> call, Throwable t) {

            }
        });
    }

    public void populaDados() {

        txtTitulo.setText(noticia.getTitulo());
        urlNoticia.setText(noticia.getFonte_url());

        String data = noticia.getDt_publicacao();
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            txtData.setText(newFormat.format(oldFormat.parse(data)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtAutor.setText(noticia.getFonte_nm());
        webDescricao.setText(noticia.getCorpo());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.placeholder(R.drawable.logonews);

        Glide.with(getApplicationContext()).load(Config.URL_SERVIDOR + noticia.getImagen_capa()).apply(requestOptions).into(imgPrincipal);
        //Glide.with(NoticiasDetalhesActivity.this).load(Config.URL_SERVIDOR + noticia.getImagem_corpo()).into(imgCorpoNoticia);

        dialog.dismiss();

    }

    public void voltar(View view) {

        onBackPressed();
        if (JsonUtils.estaconectado(getApplicationContext())) {
            carregarNoticias();

        } else {
            carregarNoticiaOfline();
            Toast.makeText(getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void linkNoticia(View view) {
        if (JsonUtils.estaconectado(getApplicationContext())) {
            String url = noticia.getFonte_url();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (JsonUtils.estaconectado(getApplicationContext())) {
            carregarNoticias();
        } else {
            carregarNoticiaOfline();
            Toast.makeText(getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        }
    }
}
