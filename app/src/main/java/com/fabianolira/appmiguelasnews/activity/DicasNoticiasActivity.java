package com.fabianolira.appmiguelasnews.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.fabianolira.appmiguelasnews.R;

import java.util.ArrayList;
import java.util.List;

public class DicasNoticiasActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imagem01, imagem02, imagem03;
    private TextView titulo, descricao;
    private List<String> listaFotosRecuperadas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicas_noticias);

        inicializarcomponentes();

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Dicas de Noticias");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_24dp);


    }

    private void inicializarcomponentes() {
        titulo = findViewById(R.id.dicasNoticiasTitulo);
        descricao = findViewById(R.id.dicasNoticiasDescricao);
        imagem01 = findViewById(R.id.dicasNoticiasImagem01);
        imagem02 = findViewById(R.id.dicasNoticiasImagem02);
        imagem03 = findViewById(R.id.dicasNoticiasImagem03);

        imagem01.setOnClickListener(this);
        imagem02.setOnClickListener(this);
        imagem03.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.dicasNoticiasImagem01:
                escolherImagem(1);
                break;
            case R.id.dicasNoticiasImagem02:
                escolherImagem(2);
                break;
            case R.id.dicasNoticiasImagem03:
                escolherImagem(3);
                break;
        }
    }

    public void escolherImagem(int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);

    }

    public void validarDadosAnuncio(View view) {

        String dicaTitulo = titulo.getText().toString();
        String dicaDescricao = descricao.getText().toString();

        if (listaFotosRecuperadas.size() != 0) {
            if (!dicaTitulo.isEmpty()) {
                if (!dicaDescricao.isEmpty()) {

                    salvarDicaNoticia();
                    finish();
                } else {
                    exibirMensagemErro("Campo Descrição vazio");
                }
            } else {
                exibirMensagemErro("Campo Titulo vazio.");
            }
        } else {
            exibirMensagemErro("Selecione pelo menos uma imagem.");
        }
    }

    private void exibirMensagemErro(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }


    public void salvarDicaNoticia() {
        Toast.makeText(this, "Dica de notícia salva com sucesso!", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Recuperar imagem
            Uri imagemSelecionada = data.getData();
            String caminhoImagem = imagemSelecionada.toString();

            //Configura imagem no ImageView
            if (requestCode == 1) {
                imagem01.setImageURI(imagemSelecionada);
            } else if (requestCode == 2) {
                imagem02.setImageURI(imagemSelecionada);
            } else if (requestCode == 3) {
                imagem03.setImageURI(imagemSelecionada);
            }
            listaFotosRecuperadas.add(caminhoImagem);

        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
