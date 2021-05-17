package com.jvamou.walls4me;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterWallpaper extends RecyclerView.Adapter<AdapterWallpaper.WallpaperViewHolder>{

    List<Wallpaper> wallpapers;

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallpaper, parent,false);
        WallpaperViewHolder viewHolder = new WallpaperViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

    public static class WallpaperViewHolder extends RecyclerView.ViewHolder {

        ImageButton imageButton;

        public WallpaperViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.item_wallpaper_individual);
        }
    }
}
