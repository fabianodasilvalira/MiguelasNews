package com.fabianolira.appmiguelasnews.fragment;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fabianolira.appmiguelasnews.DAO.NoticiaDAO;
import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.adapter.NoticiasAdapter;
import com.fabianolira.appmiguelasnews.api.NoticiasService;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Categoria;
import com.fabianolira.appmiguelasnews.model.Imagens;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.Headers;
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
    private AlertDialog dialog;
    private Noticia noticia;
    private Retrofit retrofit;
    private Imagens imagens;
    public static int CONS_STATUS = 10;

    // variaveis para paginacao
    private int qtdPaginacao = 10;
    private int page = 1;
    private boolean isLoading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount, previoustotal = 0;
    private int view_threshold = 10;
    private final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    int tamTexto = 0;
    ArrayList<String> array_noticia, array_id_noticia, array_id_categoria, array_nome_categoria, array_cor_categoria, array_titulo, array_imagem, array_autor, array_data, array_ativo;
    String[] str_noticia, str_id_noticia, str_id_categoria,str_nome_categoria, str_cor_categoria, str_titulo, str_imagem, str_autor, str_data, str_ativo;

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

        criandoArrays();

        retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_SERVIDOR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        seTiverConectado();

        metodoOnRefresh();

        return v;
    }


    private void seTiverConectado() {
        if (JsonUtils.estaconectado(getContext())) {
            //carregarNoticiasOnline();
            carregarNoticiasPaginadas();
        } else {
            Toast.makeText(getContext(), "Você não está conectado a internet!", Toast.LENGTH_SHORT).show();
            carregarNoticiasOfline();
        }
    }


    private void metodoOnRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        limpa();
                        if (JsonUtils.estaconectado(getContext())) {
                            //carregarNoticiasOnline();
                            isLoading = true;
                            page = 1;
                            carregarNoticiasPaginadas();
                        } else {
                            Toast.makeText(getContext(), "Você não está conectado a internet!", Toast.LENGTH_SHORT).show();
                            carregarNoticiasOfline();
                        }
                        // new NoticiaTask().execute(Config.URL_SERVIDOR + "api/noticia");
                    }
                }, 2000);
            }
        });
    }


    public void carregarNoticiasPaginadas() {

        carregarDialog();

        NoticiasService service = retrofit.create(NoticiasService.class);
        Call<List<Noticia>> call = service.recuperarNoticiaPaginada(page);
        //Call<List<Noticia>> call = service.recuperarNoticiaPorStatus(CONS_STATUS);

        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                if (response.isSuccessful()) {
                    listaNoticia = response.body();

                    // salvando lista de noticias no sqlite
                    NoticiaDAO noticiaDAO = new NoticiaDAO(getContext());
                    for (Noticia news : listaNoticia) {
                        noticia = news;
                        salvarQuantidadeOffline();
                        recuperarQuantidadeOffline();
                        if (Config.QTDNOTICIASOFLINE <= 20){
                            noticiaDAO.salvar(noticia);
                        }

                    }

                }

                salvarListaOfline();

                //Collections.reverse(listaNoticia);
                adapter = new NoticiasAdapter(getActivity(), listaNoticia);
                recyclerViewNoticias.setAdapter(adapter);
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                Toast.makeText(getContext(), "Erro ao se conectar com o servidor, \n " +
                        "Tente novamente em alguns minutos!", Toast.LENGTH_SHORT).show();
                System.exit(0);

            }
        });

        recyclerViewNoticias.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                NoticiaDAO noticiaDAO = new NoticiaDAO(getContext());
                if (JsonUtils.estaconectado(getContext())) {
                    if(dy > 30){
                        if (isLoading){
                            page++;
                            performancePagination();
                            isLoading = false;

                        }
                    }

                } else {

                }
            }
        });

    }

    private void performancePagination(){
        progressBar.setVisibility(View.VISIBLE);

        NoticiasService service = retrofit.create(NoticiasService.class);
        Call<List<Noticia>> call = service.recuperarNoticiaPaginada(page);

        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                // Pegar dados co header Json
                Headers headers = response.headers();
                List<String> totalPaginacao = headers.toMultimap().get("x-pagination-total-count");
                Float total = Float.parseFloat(totalPaginacao.get(0));
                Float numPaginacao = total / qtdPaginacao;
                int num = (int) Math.ceil(numPaginacao);

                if (page <= num){
                    List<Noticia> noticias = response.body();
                    adapter.addNoticias(noticias);

                    NoticiaDAO noticiaDAO = new NoticiaDAO(getContext());
                    for (Noticia news : listaNoticia) {
                        noticia = news;
                        salvarQuantidadeOffline();
                        recuperarQuantidadeOffline();
                        if (Config.QTDNOTICIASOFLINE <= 20){
                            noticiaDAO.salvar(noticia);
                        }
                    }
                    salvarListaOfline();
                    isLoading = true;
                }else{
                    isLoading = false;
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                Toast.makeText(getContext(), "Erro ao se conectar com o servidor, \n " +
                        "Tente novamente em alguns minutos!", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        });

    }

    public void carregarNoticiasOfline() {

        carregarDialog();

        NoticiaDAO noticiaDAO = new NoticiaDAO(getActivity());

        listaNoticia = noticiaDAO.listar();

        adapter = new NoticiasAdapter(getActivity(), listaNoticia);
        recyclerViewNoticias.setAdapter(adapter);
        dialog.dismiss();

        metodoOnRefresh();
    }

    private void salvarQuantidadeOffline() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        SharedPreferences.Editor editor = preferences.edit();
        Integer quantidade = Config.QTDNOTICIASOFLINE;
        editor.putInt("quantidade", quantidade);

        editor.commit();

    }
    private void recuperarQuantidadeOffline(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        if(preferences.contains("quantidade")){
            int qtd = preferences.getInt("quantidade", 20);
            Config.QTDNOTICIASOFLINE = qtd;

        }else{

        }
    }

    private void carregarDialog() {
        dialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setMessage("Carregando noticias, Aguarde!")
                .setCancelable(false)
                .build();
        dialog.show();
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

            //busca
            @Override
            public boolean onQueryTextChange(String newText) {
                tamTexto = newText.length();

                listaNoticia.clear();
                for (int i = 0; i < str_titulo.length; i++) {
                    if (str_titulo[i].toLowerCase().contains(newText.toLowerCase())) {

                        Noticia objItem = new Noticia();
                        Categoria categoria = new Categoria();
                        categoria.setNome(str_nome_categoria[i]);
                        categoria.setCor(str_cor_categoria[i]);
                        objItem.setId(str_id_noticia[i]);
                        objItem.setCategoria(categoria);
                        objItem.setTitulo(str_titulo[i]);
                        objItem.setCorpo(str_noticia[i]);
                        objItem.setDt_publicacao(str_data[i]);
                        objItem.setFonte_nm(str_autor[i]);
                        objItem.setImagen_capa(str_imagem[i]);
                        objItem.setStatus(str_ativo[i]);

                        listaNoticia.add(objItem);
                    }
                }
                //Collections.reverse(listaNoticia);
                adapter = new NoticiasAdapter(getActivity(), listaNoticia);
                recyclerViewNoticias.setAdapter(adapter);
                return false;
            }
        });

    }

    private void salvarListaOfline() {
        for (int j = 0; j < listaNoticia.size(); j++) {
            noticia = listaNoticia.get(j);

            array_noticia.add(noticia.getCorpo());
            str_noticia = array_noticia.toArray(str_noticia);

            array_id_noticia.add(String.valueOf(noticia.getId()));
            str_id_noticia = array_id_noticia.toArray(str_id_noticia);

            array_nome_categoria.add(noticia.getCategoria().getNome());
            str_nome_categoria = array_nome_categoria.toArray(str_nome_categoria);

            array_cor_categoria.add(noticia.getCategoria().getCor());
            str_cor_categoria = array_cor_categoria.toArray(str_cor_categoria);

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
    }

    private void criandoArrays() {
        array_noticia = new ArrayList<String>();
        array_id_noticia = new ArrayList<String>();
        array_id_categoria = new ArrayList<String>();
        array_nome_categoria = new ArrayList<String>();
        array_cor_categoria = new ArrayList<String>();
        array_titulo = new ArrayList<String>();
        array_imagem = new ArrayList<String>();
        array_autor = new ArrayList<String>();
        array_data = new ArrayList<String>();
        array_ativo = new ArrayList<String>();

        str_noticia = new String[array_noticia.size()];
        str_id_noticia = new String[array_id_noticia.size()];
        str_id_categoria = new String[array_id_categoria.size()];
        str_nome_categoria = new String[array_nome_categoria.size()];
        str_cor_categoria = new String[array_cor_categoria.size()];
        str_titulo = new String[array_titulo.size()];
        str_imagem = new String[array_imagem.size()];
        str_autor = new String[array_autor.size()];
        str_data = new String[array_data.size()];
        str_ativo = new String[array_ativo.size()];
    }

}