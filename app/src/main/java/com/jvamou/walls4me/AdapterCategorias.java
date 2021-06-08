package com.jvamou.walls4me;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterCategorias extends RecyclerView.Adapter<AdapterCategorias.ViewHolder> {

    ArrayList<Categoria> listaCategorias;
    Context mContext;

    Fragment fragmentAbstracto;
    HomeActivity homeActivity;


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
                .error(R.drawable.ic_imagen_error)
                .into(holder.imageView);

        holder.gridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int posicion = Integer.parseInt(String.valueOf(holder.getAdapterPosition()));

                if(posicion == 0) {
                    Intent intent = new Intent(mContext, AbstractoActivity.class);
                    mContext.startActivity(intent);
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
