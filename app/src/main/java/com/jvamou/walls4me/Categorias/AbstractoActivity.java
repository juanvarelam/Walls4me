package com.jvamou.walls4me.Categorias;

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
import com.jvamou.walls4me.Adapters.AdapterActAbstracto;
import com.jvamou.walls4me.R;
import com.jvamou.walls4me.Models.Wallpaper;

import java.util.ArrayList;

public class AbstractoActivity extends AppCompatActivity {

    //Vars globales
    RecyclerView recyclerView;
    ImageButton btnRetroceder;
    AdapterActAbstracto adapterActAbstracto;
    ArrayList<Wallpaper> wallpapersList;

    private DatabaseReference dbRef;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_abstracto);

        btnRetroceder = findViewById(R.id.act_abstracto_btn_retroceder);
        recyclerView = findViewById(R.id.act_abstracto_recycler_abstracto);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        wallpapersList = new ArrayList<>();

        //Instanciar Firebase en el directorio "abstracto" de la base de datos
        dbRef = FirebaseDatabase.getInstance().getReference("abstracto");

        adapterActAbstracto = new AdapterActAbstracto(wallpapersList, getApplicationContext());
        recyclerView.setAdapter(adapterActAbstracto);

        //Por cada wallpaper encontrado se añade al ArrayList "wallpaperList" para mostrarse en el
        //recyclerView correspondiente
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

        //Listener que redirige a la activity anterior en la que estuvo el usuario
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void obtenerDatosFirebase() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("abstracto").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    limpiarDatos();

                    //Se obtiene la url de los archivos y se añaden a la arrayList
                    for(DocumentSnapshot doc: task.getResult().getDocuments()) {
                        Wallpaper wallpaper = new Wallpaper();
                        wallpaper.url = doc.getString("url");
                        wallpapersList.add(wallpaper);

                        adapterActAbstracto = new AdapterActAbstracto(wallpapersList, getApplicationContext());
                        recyclerView.setAdapter(adapterActAbstracto);
                        adapterActAbstracto.notifyDataSetChanged();
                    }
                }else{
                    String error = task.getException().getLocalizedMessage();
                }
            }
        });
    }


    //Método que eliminar el contenido del arrayList y crea uno vacío
    private void limpiarDatos() {
        if (wallpapersList != null) {
            wallpapersList.clear();

            if (adapterActAbstracto != null) {
                adapterActAbstracto.notifyDataSetChanged();
            }
        }

        wallpapersList = new ArrayList<>();
    }
}