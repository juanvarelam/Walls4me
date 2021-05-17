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

    private ArrayList<Wallpaper> wallpapersList;
    private AdapterWallpaper adapterWallpaper;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_home);

        recyclerView = findViewById(R.id.frg_home_recycler_home);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        dbRef = FirebaseDatabase.getInstance().getReference();

        wallpapersList = new ArrayList<>();

        limpiarTodo();

        ObtenerDatosFirebase();
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

                adapterWallpaper = new AdapterWallpaper(getContext(), wallpapersList);
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