package com.jvamou.walls4me;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText authEmail;
    private EditText authPassword;
    private Button btnIniciarSesion;
    private Button btnRegistrarse;
    private Button btnRecordarPassword;
    private FirebaseAuth mAuth;

    CheckBox cboxMostrarPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        authEmail = findViewById(R.id.act_login_email);
        authPassword = findViewById(R.id.act_login_password);
        btnIniciarSesion = findViewById(R.id.act_login_btn_login);
        btnRegistrarse = findViewById(R.id.act_login_btn_registrarse);
        btnRecordarPassword = findViewById(R.id.act_login_btn_recordar_password);
        cboxMostrarPassword = findViewById(R.id.act_login_cbox_mostrar_password);

        mAuth = FirebaseAuth.getInstance();

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });

        btnRecordarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperaPassActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish(); //al ir atrás vuelve a InicialActivity
            }
        });

        cboxMostrarPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    authPassword.setTransformationMethod(null);
                } else {
                    authPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }

    public void validar() {
        String email = authEmail.getText().toString().trim().toLowerCase();
        String password = authPassword.getText().toString().trim();

        //la librería matcher comprueba que tenga estructura de email
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            authEmail.setError("Correo  inválido");
            return;
        } else {  //continúa, no muestra  nada en el mensaje de error
            authEmail.setError(null);
        }

        if(password.isEmpty() ||  password.length() < 8 ) {
            authPassword.setError("La longitud mínima es de 8 caracteres");
            return;
        } else if (!Pattern.compile("[0-9]").matcher(password).find())  {
            //comprueba que tenga un número
            authPassword.setError("Debe contener un número");
            return;
        } else {
            authPassword.setError(null);
        }

        iniciarSesion(email, password);
    }

    public void iniciarSesion(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finishAffinity();
                            Toast.makeText(LoginActivity.this, "¡Bienvenido/a a Walls4me!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}