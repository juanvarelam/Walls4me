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
import com.jvamou.walls4me.Adapters.AdapterCategorias;
import com.jvamou.walls4me.Models.Categoria;
import com.jvamou.walls4me.R;

import java.util.ArrayList;


public class FragmentCategorias extends Fragment {

    RecyclerView recyclerCategorias;
    ArrayList<Categoria> listaCategorias;
    AdapterCategorias adapterCategorias;

    private DatabaseReference dbRef;

    public FragmentCategorias() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instanciar Firebase
        dbRef = FirebaseDatabase.getInstance().getReference("categorias");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Categoria categoria = dataSnapshot.getValue(Categoria.class);
                    listaCategorias.add(categoria);
                }
                adapterCategorias = new AdapterCategorias(listaCategorias, getContext());
                recyclerCategorias.setAdapter(adapterCategorias);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frg_categorias, container, false);

        listaCategorias = new ArrayList<>();
        recyclerCategorias = v.findViewById(R.id.frg_categorias_recycler_categorias);

        adapterCategorias = new AdapterCategorias(listaCategorias, getContext());
        recyclerCategorias.setAdapter(adapterCategorias);
        recyclerCategorias.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerCategorias.setHasFixedSize(true);

        ObtenerDatosFirebase();

        return v;
    }

    private void ObtenerDatosFirebase() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categorias").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    limpiarDatos();
                    for(DocumentSnapshot doc: task.getResult().getDocuments()) {
                        Categoria categoria = new Categoria();
                        categoria.url = doc.getString("url");
                        listaCategorias.add(categoria);

                        adapterCategorias = new AdapterCategorias(listaCategorias, getContext());
                        recyclerCategorias.setAdapter(adapterCategorias);
                        adapterCategorias.notifyDataSetChanged();
                    }
                }else{
                    String error = task.getException().getLocalizedMessage();
                    Log.e("FIREBASE", error);
                }
            }
        });
    }


    private void limpiarDatos() {
        if (listaCategorias != null) {
            listaCategorias.clear();

            if (adapterCategorias != null) {
                adapterCategorias.notifyDataSetChanged();
            }
        }

        listaCategorias = new ArrayList<>();
    }
}