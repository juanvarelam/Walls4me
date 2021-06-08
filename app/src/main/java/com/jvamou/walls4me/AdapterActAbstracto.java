package com.jvamou.walls4me;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class AdapterActAbstracto extends RecyclerView.Adapter<AdapterActAbstracto.ViewHolder> {

    ArrayList<Wallpaper> wallpaperList;
    Context context;

    public AdapterActAbstracto(ArrayList<Wallpaper> wallpaperList, Context context) {
        this.wallpaperList = wallpaperList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallpaper, null, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = wallpaperList.get(position).getUrl();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_imagen_ph)
                .error(R.drawable.ic_imagen_error)
                .into(holder.imageView);

        holder.gridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WallpaperActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        GridLayout gridLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_wallpaper_imagen);
            gridLayout = itemView.findViewById(R.id.item_wallpaper_layout);
        }
    }
}