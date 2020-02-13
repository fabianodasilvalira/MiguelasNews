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

import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.activity.DetalhesActivity;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.MyViewHolder> {

    private  List<Noticia> listaNoticias;
    private Context context;
    private Noticia noticia;


    public NoticiasAdapter(List<Noticia> listaNoticias, Context context) {
        this.listaNoticias = listaNoticias;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_noticias, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        noticia = listaNoticias.get(position);

        holder.titulo.setText(noticia.getTituloNoticia());
        holder.data.setText(noticia.getDataNoticia());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticia = listaNoticias.get(position);
                String id = noticia.getIdNoticia();
                Intent intent = new Intent(context, DetalhesActivity.class);
                intent.putExtra("idNoticia", id);
                Log.d("id", "o ide e: "+ id);
                context.startActivity(intent);

            }
        });

        //Picasso.with(context).load("endereço da pasta na api" + listaNoticias.get(position).getImgNoticia()).placeholder(R.drawable.brasil).into(holder.imagem);

        holder.imagem.setImageResource(noticia.getImgNoticia());



    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagem;
        public TextView titulo, data;
        public LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.imageNoticia);
            titulo = itemView.findViewById(R.id.textNoticiasRecentes);
            data = itemView.findViewById(R.id.autorNoticia);
            linearLayout = itemView.findViewById(R.id.linearLayoutNoticias);

        }
    }


}
