package com.jvamou.walls4me.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jvamou.walls4me.Categorias.AbstractoActivity;
import com.jvamou.walls4me.Categorias.AnimalesActivity;
import com.jvamou.walls4me.Models.Categoria;
import com.jvamou.walls4me.Categorias.DeportesActivity;
import com.jvamou.walls4me.Home.HomeActivity;
import com.jvamou.walls4me.Categorias.PaisajesActivity;
import com.jvamou.walls4me.R;
import com.jvamou.walls4me.Categorias.TexturasActivity;
import com.jvamou.walls4me.Categorias.VehiculosActivity;

import java.util.ArrayList;

public class AdapterCategorias extends RecyclerView.Adapter<AdapterCategorias.ViewHolder> {

    //Vars globales
    ArrayList<Categoria> listaCategorias;
    Context mContext;
    HomeActivity homeActivity;

    //Constructor
    public AdapterCategorias(ArrayList<Categoria> listaCategorias, Context mContext) {
        this.listaCategorias = listaCategorias;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public AdapterCategorias.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.item_categoria, parent, false);

        homeActivity = new HomeActivity();

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String url = listaCategorias.get(position).getUrl();
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.ic_imagen_ph)
                .error(R.drawable.ic_imagen_ph)
                .into(holder.imageView);

        holder.gridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int posicion = Integer.parseInt(String.valueOf(holder.getAdapterPosition()));
                Intent intent;

                switch(posicion) {
                    case 0:
                        intent = new Intent(mContext, AbstractoActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(mContext, AnimalesActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(mContext, DeportesActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(mContext, PaisajesActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(mContext, TexturasActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(mContext, VehiculosActivity.class);
                        mContext.startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        GridLayout gridLayout;

        public ViewHolder (View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_categoria_imagen);
            gridLayout = itemView.findViewById(R.id.item_categoria_layout);
        }
    }



}
