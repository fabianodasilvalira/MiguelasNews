package com.fabianolira.appmiguelasnews.adapter;


import android.content.Context;
import android.content.Intent;
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
import com.fabianolira.appmiguelasnews.activity.DetalhesActivity;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load("data:image/jpeg;base64,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")
                .placeholder(R.mipmap.ic_launcher).into(holder.imagem);

        holder.titulo.setText(items.get(position).getNomeCategoria());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Config.ID_CATEGORIA = String.valueOf(categoria.getIdCategoria());
                Intent intent = new Intent(context, DetalhesActivity.class);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

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
