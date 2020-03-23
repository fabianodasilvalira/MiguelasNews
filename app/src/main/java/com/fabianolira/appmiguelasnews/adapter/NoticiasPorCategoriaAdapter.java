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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class NoticiasPorCategoriaAdapter extends RecyclerView.Adapter<NoticiasPorCategoriaAdapter.MyViewHolder> {

    private List<Noticia> items;
    private Context context;
    private Noticia noticia;


    public NoticiasPorCategoriaAdapter(Context context, List<Noticia> listaNoticias) {
        this.items = listaNoticias;
        this.context = context;
    }


    @Override
    public NoticiasPorCategoriaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_noticias, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiasPorCategoriaAdapter.MyViewHolder holder, final int position) {

        //Picasso.with(context).load("http://192.168.3.10/api_noticias_Fabiano/web/" + items.get(position)
        //.getImagem_noticia()).placeholder(R.mipmap.ic_launcher).into(holder.imagem);
        Glide.with(context).load(Config.URL_SERVIDOR + items.get(position).getImagem_capa()).into(holder.imagem);

        holder.titulo.setText(items.get(position).getTitulo());


        String data = items.get(position).getDt_publicacao();

        SimpleDateFormat oldFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newFormat =  new SimpleDateFormat("dd/MM/yyyy");

        try {
            holder.data.setText(newFormat.format(oldFormat.parse(data)));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        holder.autor.setText(items.get(position).getFonte_nm());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticia = items.get(position);
                Config.ID_NOTICIA = String.valueOf(noticia.getId());

                Log.d("id_categoria", "onClick: " + Config.ID_NOTICIA);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

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
