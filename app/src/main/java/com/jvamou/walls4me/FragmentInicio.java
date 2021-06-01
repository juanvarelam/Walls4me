package com.jvamou.walls4me;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instanciar Firebase
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frg_inicio, container, false);

        wallpapersList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.frg_home_recycler_home);

        limpiarDatos();
        ObtenerDatosFirebase();

        adapterWallpaper = new AdapterWallpaper(wallpapersList, getContext());
        recyclerView.setAdapter(adapterWallpaper);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        return v;
    }

    private void ObtenerDatosFirebase() {

        Query query = dbRef.child("imagenes");

        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                task.isSuccessful();
                recogerDatos(wallpapersList);
                if(task.isSuccessful()) {
                    ArrayList items = (ArrayList) task.getResult().getValue();

                }else{
                    task.getException().getLocalizedMessage();
                }
            }
        });
    }
    private void recogerDatos(ArrayList<Wallpaper> wallpapersList) {

        Query query = dbRef.child("imagenes");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                limpiarDatos();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Wallpaper wallpaper = new Wallpaper();

                    wallpaper.setUrl((snapshot.child("url").getValue().toString()));

                    wallpapersList.add(wallpaper);
                }

                adapterWallpaper = new AdapterWallpaper(wallpapersList, getContext());
                recyclerView.setAdapter(adapterWallpaper);
                adapterWallpaper.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void limpiarDatos() {
        if (wallpapersList != null) {
            wallpapersList.clear();

            if (adapterWallpaper != null) {
                adapterWallpaper.notifyDataSetChanged();
            }
        }

        wallpapersList = new ArrayList<>();
    }
}