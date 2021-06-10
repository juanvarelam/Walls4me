package com.jvamou.walls4me;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FragmentInfo extends Fragment {


    TextView email;
    Button btnAvisoLegal, btnCerrarSesion;
    private final static String avisoLegal = "https://firebasestorage.googleapis.com/v0/b/walls4me-56a6b.appspot.com/o/aviso_legal_walls4me.html?alt=media&token=3a1b4277-3de0-4383-ad74-771d2cead171";

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_info, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        email = v.findViewById(R.id.frg_info_email);
        btnCerrarSesion = v.findViewById(R.id.frg_info_btn_cerrar_sesion);
        btnAvisoLegal = v.findViewById(R.id.frg_info_btn_aviso_legal);

        if(user != null) {
            email.setText(user.getEmail());
        }

        btnAvisoLegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri url = Uri.parse(avisoLegal);
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                startActivity(intent);
            }
        });

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