package com.fabianolira.appmiguelasnews.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class NoticiasDetalhesActivity extends AppCompatActivity {

    private ImageView imgPrincipal, imgFavorito;
    private TextView txtTitulo, txtData, txtAutor, webDescricao;
    LinearLayout linearLayout;

    private Toolbar toolbar;
    private ActionBar actionBar;
    private AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private AlertDialog dialog;
    private TextView voltar;

    Noticia noticia;

    //WebView webDescricao;
    List<Noticia> listaNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_detalhes);


        toolbar = findViewById(R.id.toolbar);
        voltar = findViewById(R.id.voltar);

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
                     collapsingToolbarLayout.setTitle(noticia.getTitulo_noticia());
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

        listaNoticia = new ArrayList<Noticia>();

        if (JsonUtils.estaconectado(getApplicationContext())) {
            dialog = new SpotsDialog.Builder()
                    .setContext(this)
                    .setMessage("Carregando a noticia!")
                    .setCancelable(false)
                    .build();
            dialog.show();
            new NoticiaTask().execute(Config.URL_SERVIDOR + "api/noticia/" + Config.ID_NOTICIA);
            //Log.d("urlNoticia", "url da noticia: " + Config.URL_SERVIDOR + "api/noticia/" + Config.ID_NOTICIA);
        } else {
            Toast.makeText(getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
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

                JSONObject obj = new JSONObject(json);

                Noticia noticia = new Noticia();
                noticia.setId_noticias(obj.getInt("id_noticias"));
                noticia.setImagem_noticia(obj.getString("image_noticia"));
                noticia.setTitulo_noticia(obj.getString("titulo_noticia"));
                noticia.setDescricao_noticia(obj.getString("descricao_noticia"));
                noticia.setAutor_noticia(obj.getString("autor_noticia"));
                noticia.setData_noticia(obj.getString("data_noticia"));

                listaNoticia.add(noticia);


            } catch (JSONException e) {
                e.printStackTrace();

            }

            populaDados();
        }
    }

    public void populaDados() {

        noticia = listaNoticia.get(0);
        //Log.d("listaNoticia", "populaDados: " + noticia);

        txtTitulo.setText(noticia.getTitulo_noticia());
        txtData.setText(noticia.getData_noticia());
        txtAutor.setText(noticia.getAutor_noticia());
        webDescricao.setText(noticia.getDescricao_noticia());


        Glide.with(NoticiasDetalhesActivity.this).load(Config.URL_SERVIDOR + noticia.getImagem_noticia()).into(imgPrincipal);

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
