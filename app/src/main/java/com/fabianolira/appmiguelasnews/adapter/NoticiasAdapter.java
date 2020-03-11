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
import com.fabianolira.appmiguelasnews.activity.NoticiasDetalhesActivity;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;

import java.util.List;


public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.MyViewHolder> {

    private  List<Noticia> items;
    private Context context;
    private Noticia noticia;


    public NoticiasAdapter(Context context, List<Noticia> listaNoticias) {
        this.items = listaNoticias;
        this.context = context;
    }


    @Override
    public NoticiasAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_noticias, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Glide.with(context).load(Config.URL_SERVIDOR + items.get(position).getImagens().get(0).getPath()
                                     +items.get(position).getImagens().get(0).getNome() ).into(holder.imagem);


        //Log.d("imagemnoticia", "imagem : " + Glide.with(context).load(Config.URL_SERVIDOR + items.get(position).getImagens().get(0).getPath()
                //+items.get(position).getImagens().get(0).getNome() ).into(holder.imagem));

        holder.titulo.setText(items.get(position).getTitulo());
        holder.data.setText(items.get(position).getDt_publicacao());
        holder.autor.setText(items.get(position).getFonte_nm());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticia = items.get(position);
                Log.d("noticia", "onClick: " + position);
                Config.ID_NOTICIA  = String.valueOf(noticia.getId());

                Intent intent = new Intent(context, NoticiasDetalhesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagem;
        public TextView titulo, data, autor;
        public LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.imageNoticia);
            titulo = itemView.findViewById(R.id.textNoticiasRecentes);
            autor = itemView.findViewById(R.id.autorNoticia);
            data = itemView.findViewById(R.id.dataNoticia);
            linearLayout = itemView.findViewById(R.id.linearLayoutNoticias);

        }
    }


}
