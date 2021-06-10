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

    //Vars globales
    private Button btnIniciarSesion;
    private Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_inicial);

        btnIniciarSesion = findViewById(R.id.act_inicial_btn_login);
        btnRegistrarse = findViewById(R.id.act_inicial_btn_registrase);

        //Se obtiene el usuario con sesión activa
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Se comprueba si hay un usuario logueado
        if (user != null) {
            //se redirige a HomeActivity
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();  //destruye la actividad, al ir atrás se sale de la app
        }

        //Listener que al pulsar botón de iniciar sesión redirecciona a su activity correspondiente
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Listener que al pulsar botón de registrarse redirecciona a su activity correspondiente
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

