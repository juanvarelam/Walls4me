package com.jvamou.walls4me;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FragmentInicio extends Fragment {

    RecyclerView recyclerView;
    AdapterWallpaper adapterWallpaper;

    ArrayList<Wallpaper> wallpapersList;

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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        adapterWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }

    private void ObtenerDatosFirebase() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("imagenes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    limpiarDatos();
                    for(DocumentSnapshot doc: task.getResult().getDocuments()) {
                        Wallpaper wallpaper = new Wallpaper();
                        wallpaper.url = doc.getString("url");
                        wallpapersList.add(wallpaper);

                        adapterWallpaper = new AdapterWallpaper(wallpapersList, getContext());
                        recyclerView.setAdapter(adapterWallpaper);
                        adapterWallpaper.notifyDataSetChanged();
                    }
                }else{
                    String error = task.getException().getLocalizedMessage();
                    Log.e("FIREBASE", error);
                }
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