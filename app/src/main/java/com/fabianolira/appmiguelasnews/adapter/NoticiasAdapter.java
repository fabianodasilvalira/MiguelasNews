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
import com.fabianolira.appmiguelasnews.model.Noticia;
import java.util.List;


public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.MyViewHolder> {

    private  List<Noticia> listaNoticias;
    private Context context;


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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Noticia noticia = listaNoticias.get(position);
        holder.titulo.setText(noticia.getTituloNoticia());
        holder.data.setText(noticia.getDataNoticia());
        holder.imagem.setImageResource(noticia.getImgNoticia());



    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imagem;
        TextView titulo, data;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.imageNoticia);
            titulo = itemView.findViewById(R.id.textNoticiasRecentes);
            data = itemView.findViewById(R.id.textDataNoticia);

        }
    }


}
