package com.jvamou.walls4me;

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

import java.util.ArrayList;

public class AdapterActVehiculos extends RecyclerView.Adapter<AdapterActVehiculos.ViewHolder> {

    ArrayList<Wallpaper> wallpaperList;
    Context mContext;


    public AdapterActVehiculos(ArrayList<Wallpaper> wallpaperList, Context mContext) {
        this.wallpaperList = wallpaperList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AdapterActVehiculos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.item_wallpaper, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String url = wallpaperList.get(position).getUrl();
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.ic_imagen_ph)
                .error(R.drawable.ic_imagen_error)
                .into(holder.imageView);

        holder.gridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WallpaperActivity.class);

                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra("url", url);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);            }
        });

    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        GridLayout gridLayout;

        public ViewHolder (View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_wallpaper_imagen);
            gridLayout = itemView.findViewById(R.id.item_wallpaper_layout);
        }
    }

}