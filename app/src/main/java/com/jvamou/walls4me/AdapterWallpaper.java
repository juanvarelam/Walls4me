package com.jvamou.walls4me;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterWallpaper extends RecyclerView.Adapter<AdapterWallpaper.WallpaperViewHolder>{

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class WallpaperViewHolder extends RecyclerView.ViewHolder {

        ImageButton imageButton;

        public WallpaperViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.item_wallpaper_individual);
        }
    }
}
