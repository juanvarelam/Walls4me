package com.jvamou.walls4me.Auth;

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
import com.jvamou.walls4me.Home.HomeActivity;
import com.jvamou.walls4me.R;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    //Vars globales
    private EditText authEmail;
    private EditText authPassword;
    private Button btnIniciarSesion;
    private Button btnRegistrarse;
    private Button btnRecordarPassword;
    private FirebaseAuth mAuth;
    private CheckBox cboxMostrarPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        authEmail = findViewById(R.id.act_login_email);
        authPassword = findViewById(R.id.act_login_password);
        btnIniciarSesion = findViewById(R.id.act_login_btn_login);
        btnRegistrarse = findViewById(R.id.act_login_btn_registrarse);
        btnRecordarPassword = findViewById(R.id.act_login_btn_pass_olvidada);
        cboxMostrarPassword = findViewById(R.id.act_login_cbox_mostrar_password);

        //Se inicializa Firebase
        mAuth = FirebaseAuth.getInstance();

        //Listener que llama al método validar()
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });

        //Listener que redirige a la activity RecuperaPassActivity
        btnRecordarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperaPassActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Listener que redirige a la activity RegisterActivity
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish(); //al ir atrás vuelve a InicialActivity
            }
        });

        //Listener que muestra u oculta el contenido del campo contraseña
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

    //Método que comprueba los campos de inicio de sesión
    public void validar() {
        String email = authEmail.getText().toString().trim().toLowerCase();
        String password = authPassword.getText().toString().trim();

        //.matcher comprueba que tenga estructura de email
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            String msg = getApplicationContext().getResources().getString(R.string.act_login_correo_invalido);
            authEmail.setError(msg);  //muestra un aviso en ese campo
            return;
        } else {  //continúa, no muestra  nada en el mensaje de error
            authEmail.setError(null);
        }

        if (password.isEmpty() ||  password.length() < 8 ) {
            String msg = getApplicationContext().getResources().getString(R.string.act_login_longitud_password);
            authPassword.setError(msg);
            return;
        } else if (!Pattern.compile("[0-9]").matcher(password).find())  {
            //comprueba que tenga un número
            String msg = getApplicationContext().getResources().getString(R.string.act_login_numero_password);
            authPassword.setError(msg);
            return;
        } else {
            authPassword.setError(null);
        }

        iniciarSesion(email, password);
    }

    //Método que realiza el inicio de sesión con los datos recogidos
    public void iniciarSesion(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {  //si los datos son válidos continúa
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finishAffinity();

                            String msg = getApplicationContext().getResources().getString(R.string.act_login_mensaje_bienvenida);
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } else {
                            String msg = getApplicationContext().getResources().getString(R.string.act_login_mensaje_error);
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}