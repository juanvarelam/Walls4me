package com.jvamou.walls4me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.ic_imagen_ph)
                .error(R.drawable.ic_imagen_error)
                .into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WallpaperActivity.class);
                intent.putExtra("url", wallpaperList.get(position).getUrl());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    public void setOnClickListener (View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if(clickListener != null) {
            clickListener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageButton;

        public ViewHolder (View itemView) {
            super(itemView);

            imageButton = itemView.findViewById(R.id.item_wallpaper_imagen);
        }
    }

}
