package com.fabianolira.appmiguelasnews.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.adapter.NoticiasAdapter;
import com.fabianolira.appmiguelasnews.model.Noticia;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasRecentesFragment extends Fragment {

    RecyclerView recyclerViewNoticias;
    private NoticiasAdapter adapter;
    private List<Noticia> listaNoticia = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_noticias_recentes, container, false);

        setHasOptionsMenu(true);

        recyclerViewNoticias = v.findViewById(R.id.recyclerViewNoticias);

        // listagem de Noticias
        this.criarNoticias();

        //configurar adapter
        adapter = new NoticiasAdapter(listaNoticia, getActivity());

        //configurar recyclerview
        // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNoticias.setLayoutManager(layoutManager);
        recyclerViewNoticias.setHasFixedSize(true);
        recyclerViewNoticias.setAdapter(adapter);

        return v;
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
                if (!hasFocus){
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("",false);
                }
            }
        });
    }

    public void pesquisarNoticiasRecentes(String texto){
        Log.d("pesquisaaandoooo....", texto);
    }

    private void criarNoticias() {

        Noticia noticia = new Noticia("Titulo","22\10\\2019", 2);

        noticia = new Noticia("Olé! Brasil vence novamente!","22/11/2019",  R.drawable.brasil);
        listaNoticia.add(noticia);

        noticia = new Noticia("Multas muito altas assolam os brasileiros","17/06/2019",  R.drawable.multas);
        listaNoticia.add(noticia);

        noticia = new Noticia("Super lua será vista por Teresinenses","12/12/2019",  R.drawable.superlua);
        listaNoticia.add(noticia);


    }

}
