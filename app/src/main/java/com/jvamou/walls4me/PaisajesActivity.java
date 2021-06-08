package com.jvamou.walls4me;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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

import java.util.ArrayList;

public class PaisajesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton btnRetroceder;
    AdapterActPaisajes adapterActPaisajes;

    ArrayList<Wallpaper> wallpapersList;

    private DatabaseReference dbRef;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_paisajes);

        btnRetroceder = findViewById(R.id.act_paisajes_btn_retroceder);
        recyclerView = findViewById(R.id.act_paisajes_recycler_paisajes);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        wallpapersList = new ArrayList<>();

        //Instanciar Firebase
        dbRef = FirebaseDatabase.getInstance().getReference("paisajes");

        adapterActPaisajes = new AdapterActPaisajes(wallpapersList, getApplicationContext());
        recyclerView.setAdapter(adapterActPaisajes);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Wallpaper wallpaper = dataSnapshot.getValue(Wallpaper.class);
                    wallpapersList.add(wallpaper);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        obtenerDatosFirebase();

        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void obtenerDatosFirebase() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("paisajes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    limpiarDatos();
                    for(DocumentSnapshot doc: task.getResult().getDocuments()) {
                        Wallpaper wallpaper = new Wallpaper();
                        wallpaper.url = doc.getString("url");
                        wallpapersList.add(wallpaper);

                        adapterActPaisajes = new AdapterActPaisajes(wallpapersList, getApplicationContext());
                        recyclerView.setAdapter(adapterActPaisajes);
                        adapterActPaisajes.notifyDataSetChanged();
                    }
                }else{
                    String error = task.getException().getLocalizedMessage();
                }
            }
        });
    }


    private void limpiarDatos() {
        if (wallpapersList != null) {
            wallpapersList.clear();

            if (adapterActPaisajes != null) {
                adapterActPaisajes.notifyDataSetChanged();
            }
        }

        wallpapersList = new ArrayList<>();
    }
}