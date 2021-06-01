package com.jvamou.walls4me;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterWallpaper extends RecyclerView.Adapter<AdapterWallpaper.ViewHolder> {

    ArrayList<Wallpaper> wallpaperList;
    Context mContext;


    public AdapterWallpaper(ArrayList<Wallpaper> wallpaperList, Context mContext) {
        this.wallpaperList = wallpaperList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AdapterWallpaper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.item_wallpaper, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String url = wallpaperList.get(position).getUrl();
        Log.d("imagen", url);
        Glide.with(mContext)//
                .load(url)
                .placeholder(R.drawable.ic_imagen_ph)
                .error(R.drawable.ic_imagen_error)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder (View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_wallpaper_imagen);
        }
    }

}
