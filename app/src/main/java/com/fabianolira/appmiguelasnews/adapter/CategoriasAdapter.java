package com.fabianolira.appmiguelasnews.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.activity.CategoriaDetalhesActivity;
import com.fabianolira.appmiguelasnews.activity.NoticiasDetalhesActivity;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
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

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.placeholder(R.drawable.logonews);

        Glide.with(context).load(Config.URL_SERVIDOR + items.get(position).getImagem()).apply(requestOptions).into(holder.imagem);

        holder.titulo.setText(items.get(position).getNome());

        try {
            holder.linearLayoutCorCategoria.setBackgroundColor(Color.parseColor(items.get(position).getCor()));
        }catch(IllegalArgumentException e){
            holder.linearLayoutCorCategoria.setBackgroundColor(Color.parseColor("#0199CA"));

        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoria = items.get(position);

                Config.ID_CATEGORIA = categoria.getId();
                Config.TITULO_CATEGORIA = categoria.getNome();

                Log.d("IDCATEGORIA", "onClick: " + Config.ID_CATEGORIA );
                if (JsonUtils.estaconectado(context)) {
                    Intent intent = new Intent(context, CategoriaDetalhesActivity.class);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Você não está conectado a internet", Toast.LENGTH_SHORT).show();
                }

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
        LinearLayout linearLayoutCorCategoria;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.imageCategoria);
            titulo = itemView.findViewById(R.id.textTituloCategoria);
            linearLayoutCorCategoria = itemView.findViewById(R.id.linearLayoutCorCategoria);
            linearLayout = itemView.findViewById(R.id.linearLayoutCategoria);

        }
    }

}
