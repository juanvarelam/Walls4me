package com.jvamou.walls4me.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

public class RegisterActivity extends AppCompatActivity {

    //Vars globales
    private EditText authNombre;
    private EditText authEmail;
    private EditText authPassword;
    private EditText authConfirmPassword;
    private CheckBox cboxMostrarPassword;
    private Button authBtnRegistro;
    private Button btnIniciarSesion;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);

        //Se obtiene instancia de Firebase
        mAuth = FirebaseAuth.getInstance();

        authNombre = findViewById(R.id.act_register_txt_nombre);
        authEmail = findViewById(R.id.act_register_txt_email);
        authPassword = findViewById(R.id.act_register_txt_password);
        authConfirmPassword = findViewById(R.id.act_register_txt_confirm_password);
        cboxMostrarPassword = findViewById(R.id.act_register_cbox_mostrar_password);
        authBtnRegistro = findViewById(R.id.act_register_btn_registrarse);
        btnIniciarSesion = findViewById(R.id.act_register_btn_login);

        //Listener que redirecciona a LoginActivity
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); //al ir atrás vuelve a InicialActivity
            }
        });


        //Listener que llama al método validar()
        authBtnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });

        //Listener que muestra u oculta el contenido de los campos contraseña y confirmar contraseña
        cboxMostrarPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    authPassword.setTransformationMethod(null);
                    authConfirmPassword.setTransformationMethod(null);
                } else {
                    authPassword.setTransformationMethod(new PasswordTransformationMethod());
                    authConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }

    public void validar() {
        String nombre = authNombre.getText().toString();
        String email = authEmail.getText().toString().trim().toLowerCase();
        String password = authPassword.getText().toString().trim();
        String confirmPassword = authConfirmPassword.getText().toString().trim();

        if(nombre.isEmpty()) {
            String msg = getApplicationContext().getResources().getString(R.string.act_register_introducir_nombre);
            authNombre.setError(msg);
            return;
        } else {
            authNombre.setError(null);
        }

        //la librería matcher comprueba que tenga estructura de email
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            String msg = getApplicationContext().getResources().getString(R.string.act_register_correo_invalido);
            authEmail.setError(msg);
            return;
        } else {  //continúa, no muestra  nada en el mensaje de error
            authEmail.setError(null);
        }

        if(password.isEmpty() ||  password.length() < 8 ) {
            String msg = getApplicationContext().getResources().getString(R.string.act_register_longitud_password);
            authPassword.setError(msg);
            return;

            //comprueba que tenga un número
        } else if (!Pattern.compile("[0-9]").matcher(password).find())  {
            String msg = getApplicationContext().getResources().getString(R.string.act_register_numero);
            authPassword.setError(msg);
            return;
        } else {
            authPassword.setError(null);
        }

        if(!confirmPassword.equals(password)) {
            String msg = getApplicationContext().getResources().getString(R.string.act_register_passwords);
            authPassword.setError(msg);
            return;
        } else {  //después de comprobar todos los campos llama al método registrar()
            registrar(email, password);
        }
    }

    public void registrar(String email, String password) {

        //Se crea un usuario con el email y la contraseña previamente introducidas
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finishAffinity();

                            String msg = getApplicationContext().getResources().getString(R.string.act_register_bienvenida);
                            Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setTitle("Error");
                            String msg = getApplicationContext().getResources().getString(R.string.act_register_correo_uso);
                            builder.setMessage(msg);
                            builder.setPositiveButton("OK", null);

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
    }
}