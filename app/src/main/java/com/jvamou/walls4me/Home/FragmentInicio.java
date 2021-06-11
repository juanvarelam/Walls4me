package com.jvamou.walls4me.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jvamou.walls4me.Adapters.AdapterFrgInicio;
import com.jvamou.walls4me.R;
import com.jvamou.walls4me.Models.Wallpaper;

import java.util.ArrayList;

public class FragmentInicio extends Fragment {

    //Vars globales
    RecyclerView recyclerView;
    AdapterFrgInicio adapterFrgInicio;
    ArrayList<Wallpaper> wallpapersList;
    private DatabaseReference dbRef;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instanciar Firebase
        dbRef = FirebaseDatabase.getInstance().getReference("imagenes");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Wallpaper wallpaper = dataSnapshot.getValue(Wallpaper.class);
                    wallpapersList.add(wallpaper);
                }
                adapterFrgInicio = new AdapterFrgInicio(wallpapersList, getContext());
                recyclerView.setAdapter(adapterFrgInicio);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frg_inicio, container, false);

        wallpapersList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.frg_inicio_recycler_inicio);

        ObtenerDatosFirebase();

        adapterFrgInicio = new AdapterFrgInicio(wallpapersList, getContext());
        recyclerView.setAdapter(adapterFrgInicio);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);


        return v;
    }

    private void ObtenerDatosFirebase() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("imagenes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    limpiarDatos();

                    //Se obtiene la url de los archivos y se añaden a la arrayList
                    for(DocumentSnapshot doc: task.getResult().getDocuments()) {
                        Wallpaper wallpaper = new Wallpaper();
                        wallpaper.url = doc.getString("url");
                        wallpapersList.add(wallpaper);

                        adapterFrgInicio = new AdapterFrgInicio(wallpapersList, getContext());
                        recyclerView.setAdapter(adapterFrgInicio);
                        adapterFrgInicio.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Método que eliminar el contenido del arrayList y crea uno vacío
    private void limpiarDatos() {
        if (wallpapersList != null) {
            wallpapersList.clear();

            if (adapterFrgInicio != null) {
                adapterFrgInicio.notifyDataSetChanged();
            }
        }

        wallpapersList = new ArrayList<>();
    }
}