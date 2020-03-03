package com.fabianolira.appmiguelasnews.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.activity.CategoriaDetalhesActivity;
import com.fabianolira.appmiguelasnews.activity.NoticiasDetalhesActivity;
import com.fabianolira.appmiguelasnews.model.Categoria;
import com.fabianolira.appmiguelasnews.util.Config;

import java.util.List;


public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.MyViewHolder> {

    private List<Categoria> items;
    private Context context;
    private Categoria categoria;


    public CategoriasAdapter(List<Categoria> listaCategoria, Context context) {
        this.items = listaCategoria;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriasAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categorias, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Glide.with(context).load(Config.URL_SERVIDOR + items.get(position).getImgCategoria()).into(holder.imagem);
        //Log.d("Categoria", "onBindViewHolder: " + Config.URL_SERVIDOR + items.get(position).getImgCategoria());

        holder.titulo.setText(items.get(position).getNomeCategoria());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoria = items.get(position);
                Config.ID_CATEGORIA = categoria.getIdCategoria();
                Config.TITULO_CATEGORIA = categoria.getNomeCategoria();

                //Log.d("categoria", "onClick: " + Config.ID_CATEGORIA + categoria.getNomeCategoria());
                Intent intent = new Intent(context, CategoriaDetalhesActivity.class);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imagem;
        TextView titulo;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.imageCategoria);
            titulo = itemView.findViewById(R.id.textTituloCategoria);
            linearLayout = itemView.findViewById(R.id.linearLayoutCategoria);

        }
    }


}
