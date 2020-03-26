package com.fabianolira.appmiguelasnews.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.activity.NoticiasDetalhesActivity;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Noticia;
import com.fabianolira.appmiguelasnews.util.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.MyViewHolder> {

    private List<Noticia> items;
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

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.placeholder(R.drawable.logonews);

        Glide.with(context).load(Config.URL_SERVIDOR + items.get(position).getImagen_capa()).apply(requestOptions).into(holder.imagem);

        final Noticia noticia = items.get(position);
        holder.titulo.setText(noticia.getTitulo());
        holder.textoCard.setText(noticia.getCategoria().getNome());
        holder.cardView.setBackgroundColor(Color.parseColor(items.get(position).getCategoria().getCor()));

        Log.i("IdNoticia", "onClick: " + noticia.getTitulo());

        String data = noticia.getDt_publicacao();

        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            holder.data.setText(newFormat.format(oldFormat.parse(data)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.autor.setText(noticia.getFonte_nm());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Config.ID_NOTICIA = noticia.getId();

                Log.i("IdNoticia", "onClick: " + Config.ID_NOTICIA);

                Intent intent = new Intent(context, NoticiasDetalhesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.i("tamanho", "tamanho: " + items.size());
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagem, imagemOfline;
        public TextView titulo, data, autor, textoCard;
        public LinearLayout linearLayout;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.imageNoticia);
            imagemOfline = itemView.findViewById(R.id.imageNoticia);
            titulo = itemView.findViewById(R.id.textNoticiasRecentes);
            textoCard = itemView.findViewById(R.id.tituloCategoria);
            autor = itemView.findViewById(R.id.autorNoticia);
            data = itemView.findViewById(R.id.dataNoticia);
            linearLayout = itemView.findViewById(R.id.linearLayoutNoticias);
            cardView = itemView.findViewById(R.id.corCategoria);

        }
    }

}
