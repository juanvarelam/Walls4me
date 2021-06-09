package com.jvamou.walls4me;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FragmentAjustes extends Fragment {


    TextView email;
    Button btnCerrarSesion;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_ajustes, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        email = v.findViewById(R.id.frg_ajustes_email);
        btnCerrarSesion = v.findViewById(R.id.frg_ajustes_btn_cerrar_sesion);

        if(user != null) {
            email.setText(user.getEmail());
        }

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), InicialActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();  //al darle a retroceder sale de la aplicación
            }
        });

        return v;
    }
}