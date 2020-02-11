package com.fabianolira.appmiguelasnews.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.model.Categoria;

import java.util.List;


public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.MyViewHolder> {

    private List<Categoria> listaCategoria;
    private Context context;


    public CategoriasAdapter(List<Categoria> listaCategoria, Context context) {
        this.listaCategoria = listaCategoria;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categorias, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Categoria categoria = listaCategoria.get(position);
        holder.titulo.setText(categoria.getNomeCategoria());
        holder.imagem.setImageResource(categoria.getImgCategoria());
    }

    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imagem;
        TextView titulo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.imageCategoria);
            titulo = itemView.findViewById(R.id.textTituloCategoria);

        }
    }


}
