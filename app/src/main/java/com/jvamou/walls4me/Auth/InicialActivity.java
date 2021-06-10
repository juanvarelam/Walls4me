package com.jvamou.walls4me.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jvamou.walls4me.Home.HomeActivity;
import com.jvamou.walls4me.R;

public class InicialActivity extends AppCompatActivity {

    private Button btnIniciarSesion;
    private Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_inicial);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        btnIniciarSesion = findViewById(R.id.act_inicial_btn_login);
        btnRegistrarse = findViewById(R.id.act_inicial_btn_registrase);

        //listener que al pulsar botón de iniciar sesión redirecciona a activity correspondiente
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //listener que al pulsar botón de registrarse redirecciona a activity correspondiente
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

