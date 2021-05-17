package com.jvamou.walls4me;

import android.content.Context;
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

    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Wallpaper> wallpaperList;

    public AdapterWallpaper(Context mContext, ArrayList<Wallpaper> wallpaperList) {
        this.mContext = mContext;
        this.wallpaperList = wallpaperList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wallpaper, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .load(wallpaperList.get(position).getUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

}
