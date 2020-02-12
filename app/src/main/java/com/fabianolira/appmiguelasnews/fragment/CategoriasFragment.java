package com.fabianolira.appmiguelasnews.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.adapter.CategoriasAdapter;
import com.fabianolira.appmiguelasnews.model.Categoria;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriasFragment extends Fragment {
    RecyclerView recyclerViewCategoria;
    private CategoriasAdapter adapter;
    private List<Categoria> listaCategoria = new ArrayList<>();
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_categorias, container, false);
        setHasOptionsMenu(true);


        recyclerViewCategoria = v.findViewById(R.id.recyclerViewCategoria);



        // listagem de Noticias
        this.criarNoticias();

        //configurar adapter
        adapter = new CategoriasAdapter(listaCategoria, getActivity());

        //configurar recyclerview
        // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewCategoria.setLayoutManager(layoutManager);
        recyclerViewCategoria.setHasFixedSize(true);
        recyclerViewCategoria.setAdapter(adapter);





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


    private void criarNoticias() {

        Categoria categoria = new Categoria("Titulo", 2);

        categoria = new Categoria("ESPORTES",  R.drawable.brasil);
        listaCategoria.add(categoria);

        categoria = new Categoria("POLITICA", R.drawable.politica);
        listaCategoria.add(categoria);

        categoria = new Categoria("EDUCAÇÃO", R.drawable.educacao);
        listaCategoria.add(categoria);

        categoria = new Categoria("SAÚDE", R.drawable.saude);
        listaCategoria.add(categoria);


    }

}
