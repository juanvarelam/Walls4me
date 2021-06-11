package com.jvamou.walls4me.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.jvamou.walls4me.R;

public class RecuperaPassActivity extends AppCompatActivity {

    //Vars globales
    private EditText email;
    private Button btnRecuperaPass, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recupera_pass);

        email = findViewById(R.id.act_recupera_pass_email);
        btnRecuperaPass = findViewById(R.id.act_recupera_pass_btn_recuperar_pass);
        btnVolver = findViewById(R.id.act_recupera_pass_volver);

        //Listener que redirige a LoginActivity
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecuperaPassActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Listener que llama al método validarEmail()
        btnRecuperaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarEmail();
            }
        });
    }

    //Método que comprueba que el formato del correo sea válido
    private void validarEmail() {
        String correo = email.getText().toString().trim();

        if(correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            String msg = getApplicationContext().getResources().getString(R.string.act_recupera_pass_correo_invalido);
            email.setError(msg);
            return;
        }

        enviarCorreo(correo);
    }


    public void enviarCorreo(String correo) {

        //Se inicializa Firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String direccionCorreo = correo;

        //Método que envía el correo al usuario
        auth.sendPasswordResetEmail(direccionCorreo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String msg = getApplicationContext().getResources().getString(R.string.act_recupera_pass_correo_enviado);
                            Toast.makeText(RecuperaPassActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RecuperaPassActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String msg = getApplicationContext().getResources().getString(R.string.act_recupera_pass_correo_invalido);
                            AlertDialog.Builder builder = new AlertDialog.Builder(RecuperaPassActivity.this);
                            builder.setTitle(msg);
                            builder.setPositiveButton("OK", null);

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
    }

    //Método que al hacer click en ir atrás nos redirecciona a LoginActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(RecuperaPassActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}