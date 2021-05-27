package com.jvamou.walls4me;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentInicio extends Fragment {

    RecyclerView recyclerView;
    AdapterWallpaper adapterWallpaper;

    ArrayList<Wallpaper> wallpapersList;
    private Context mContext;

    private DatabaseReference dbRef;

    public FragmentInicio() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_inicio, container, false);

        dbRef = FirebaseDatabase.getInstance().getReference();

        wallpapersList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.frg_home_recycler_home);


        limpiarTodo();
        ObtenerDatosFirebase();

        adapterWallpaper = new AdapterWallpaper(wallpapersList, getContext());
        recyclerView.setAdapter(adapterWallpaper);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        return v;
    }

    private void ObtenerDatosFirebase() {

        Query query = dbRef.child("imagenes");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                limpiarTodo();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Wallpaper wallpapers = new Wallpaper();

                    wallpapers.setUrl((snapshot.child("url").getValue().toString()));

                    wallpapersList.add(wallpapers);
                }

                adapterWallpaper = new AdapterWallpaper(wallpapersList, getContext());
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