package com.jvamou.walls4me;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    RecyclerView recyclerView;

    private DatabaseReference dbRef;

    ArrayList<Wallpaper> wallpapersList;
    private AdapterWallpaper adapterWallpaper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_home, container, false);

        wallpapersList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.frg_home_recycler_home);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        AdapterWallpaper adapterWallpaper = new AdapterWallpaper(wallpapersList);
        recyclerView.setAdapter(adapterWallpaper);
        adapterWallpaper.notifyDataSetChanged();

        adapterWallpaper.notifyDataSetChanged();


        dbRef = FirebaseDatabase.getInstance().getReference();

        wallpapersList = new ArrayList<>();

        limpiarTodo();

        ObtenerDatosFirebase();

        return view;
    }

    private void ObtenerDatosFirebase() {
        Query query = dbRef.child("Imagenes");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Wallpaper wallpapers = new Wallpaper();

                    wallpapers.setUrl(snapshot.child("url").getValue().toString());

                    wallpapersList.add(wallpapers);
                }

                AdapterWallpaper adapterWallpaper = new AdapterWallpaper(wallpapersList);
                recyclerView.setAdapter(adapterWallpaper);
                adapterWallpaper.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void limpiarTodo() {
        if (wallpapersList != null) {
            wallpapersList.clear();

            if (adapterWallpaper != null) {
                adapterWallpaper.notifyDataSetChanged();
            }
        }

        wallpapersList = new ArrayList<>();
    }
}