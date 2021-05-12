package com.jvamou.walls4me;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    //variables para datos de registro
    private EditText authEmail;
    private EditText authPassword;
    private EditText authConfirmPassword;
    private Button authBtnRegistro;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);

        //referencia a los elementos
        authEmail = findViewById(R.id.act_register_txt_email);
        authPassword = findViewById(R.id.act_register_txt_password);
        authConfirmPassword = findViewById(R.id.act_register_txt_confirm_password);
        authBtnRegistro = findViewById(R.id.act_register_btn_registrarse);

        authBtnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public void validar() {
        String email = authEmail.getText().toString().trim().toLowerCase();
        String password = authPassword.getText().toString().trim();
        String confirmPassword = authConfirmPassword.getText().toString().trim();

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

        if(!confirmPassword.equals(password)) {
            authPassword.setError("Las contraseñas deben coincidir");
            return;
        } else {
            registrar(email, password);
        }
    }

    public void registrar(String email, String password) {

    }
}