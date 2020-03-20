package com.fabianolira.appmiguelasnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fabianolira.appmiguelasnews.R;

public class ImagemAdapter extends RecyclerView.Adapter<ImagemAdapter.MyViewHolder> {

    private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imagem_detalhe, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.nome.setText("Titulo da imagem aqui...");
        holder.imagem.setImageResource(R.drawable.logonews);

        // Glide.with(context).load("http://goo.gl/gEgYUd").into(holder.imagem);

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // private TextView nome;
        private ImageView imagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //nome = itemView.findViewById(R.id.textViewDetalhe);
            imagem = itemView.findViewById(R.id.imagemDetalhe);
        }
    }
}
