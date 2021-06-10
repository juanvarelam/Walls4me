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

    private EditText email;
    private Button btnRecuperaPass, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recupera_pass);

        email = findViewById(R.id.act_recupera_pass_email);
        btnRecuperaPass = findViewById(R.id.act_recupera_pass_btn_recuperar_pass);
        btnVolver = findViewById(R.id.act_recupera_pass_volver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecuperaPassActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRecuperaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarEmail();
            }
        });
    }

    private void validarEmail() {
        String correo = email.getText().toString().trim();

        if(correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            email.setError("Correo inválido");
            return;
        }

        enviarCorreo(correo);
    }

    //método que al hacer click en ir atrás nos redirecciona a LoginActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(RecuperaPassActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void enviarCorreo(String correo) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String direccionCorreo = correo;

        auth.sendPasswordResetEmail(direccionCorreo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RecuperaPassActivity.this, "Correo enviado!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RecuperaPassActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RecuperaPassActivity.this);
                            builder.setTitle("Correo inválido.");
                            builder.setPositiveButton("OK", null);

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
    }
}