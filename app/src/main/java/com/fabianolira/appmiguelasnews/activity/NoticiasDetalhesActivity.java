package com.fabianolira.appmiguelasnews.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.adapter.ImagemAdapter;
import com.fabianolira.appmiguelasnews.adapter.NoticiasAdapter;
import com.fabianolira.appmiguelasnews.api.NoticiasService;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Imagens;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoticiasDetalhesActivity extends AppCompatActivity {

    private ImageView imgPrincipal, imgCorpoNoticia;
    private TextView txtTitulo, txtData, txtAutor, webDescricao;
    LinearLayout linearLayout;
    private RecyclerView recyclerImagens;

    private Toolbar toolbar;
    private ActionBar actionBar;
    private AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private AlertDialog dialog;
    private TextView voltar;
    Retrofit retrofit;

    Noticia noticia;

    //WebView webDescricao;
    List<Noticia> listaNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_detalhes);


        toolbar = findViewById(R.id.toolbar);
        voltar = findViewById(R.id.voltar);

        recyclerImagens = findViewById(R.id.recyclerViewImagensDetalhes);

        //Definir Layout
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerImagens.setLayoutManager(layoutManager);
        //definir adaptador
        ImagemAdapter adapter = new ImagemAdapter();
        recyclerImagens.setAdapter(adapter);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
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
                if(scrollRange==-1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle(noticia.getTitulo());
                    estaMostrando = true;

                }else{
                    collapsingToolbarLayout.setTitle("");
                    estaMostrando = false;
                }
            }
        });

        collapsingToolbarLayout = findViewById(R.id.ColalapsingToolbar);


        imgPrincipal = findViewById(R.id.imagemPrincipal);
        // imgFavorito = findViewById(R.id.)

        txtTitulo = findViewById(R.id.textTituloDetalhes);
        txtAutor = findViewById(R.id.textAutor);
        txtData = findViewById(R.id.textDataDetalheso);
        webDescricao = findViewById(R.id.webDescricao);
        imgPrincipal = findViewById(R.id.imagemPrincipal);
        imgCorpoNoticia = findViewById(R.id.imagemDetalhe);
        listaNoticia = new ArrayList<Noticia>();

        if (JsonUtils.estaconectado(getApplicationContext())) {
            dialog = new SpotsDialog.Builder()
                    .setContext(this)
                    .setMessage("Carregando a noticia!")
                    .setCancelable(false)
                    .build();
            dialog.show();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_SERVIDOR)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            carregarNoticias();
            //Log.d("urlNoticia", "url da noticia: " + Config.URL_SERVIDOR + "api/noticia/" + Config.ID_NOTICIA);
        } else {
            Toast.makeText(getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        }

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
                    //Log.d("Imagem capa", "onResponse: " + noticia.getImagen_capa());



                        /*List<Imagens> imagensList = new ArrayList<>();
                        Imagens imagens = new Imagens();
                        imagens.setNome(news.getImagens().get(0).getNome());
                        imagens.setPath(news.getImagens().get(0).getPath());
                        imagensList.add(imagens);

                        noticia.setImagens(imagensList);
                        //Log.d("Entrou ->", "onResponse: " + news.getImagens().get(0).getPath()+news.getImagens().get(0).getNome());
                        Log.d("Entrou ->", "onResponse: " + noticia.getImagens().get(0).getPath()+noticia.getImagens().get(0).getNome());

                         */


                }
            }

            @Override
            public void onFailure(Call<Noticia> call, Throwable t) {

                Log.d("entrou no erro", "resultado: " + t.getLocalizedMessage());
            }
        });
    }

    public void populaDados() {


        //Log.d("listaNoticia", "populaDados: " + noticia);

        txtTitulo.setText(noticia.getTitulo());
        txtData.setText(noticia.getDt_publicacao());
        txtAutor.setText(noticia.getFonte_nm());
        webDescricao.setText(noticia.getCorpo());


        Glide.with(NoticiasDetalhesActivity.this).load(Config.URL_SERVIDOR + noticia.getImagen_capa()).into(imgPrincipal);
        //Glide.with(NoticiasDetalhesActivity.this).load(Config.URL_SERVIDOR + noticia.getImagem_corpo()).into(imgCorpoNoticia);

        Log.d("imagem_Image", "populaDados: " + Config.URL_SERVIDOR + noticia.getImagen_capa()+"/");
        dialog.dismiss();
        /* WebSettings webSettings = webDescricao.getSettings();
        Resources res = getResources();
        int tamFont = 20;
        webSettings.setJavaScriptEnabled(true);
        String mimeType = "text/html; charset=UTF8";
        String encoding = "utf-8";
        String textoHtml = noticia.getDescricao_noticia();
        String estrutura =
                "<html><head>"
                + "<style type=\"text/css\">body{color: #525252;}"
                + "</style></head>"
                + "<body>"
                + textoHtml
                + "</body></html>";
        webDescricao.loadData(estrutura, mimeType, encoding);
        Log.d("popula dados", "populaDados: " + textoHtml);*/

    }

    public void voltar(View view) {
        onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
