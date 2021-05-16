package com.jvamou.walls4me;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RecuperaPassActivity extends AppCompatActivity {

    private EditText email;
    private Button btnRecuperaPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recupera_pass);

        email = findViewById(R.id.act_recupera_pass_email);
        btnRecuperaPass = findViewById(R.id.act_recupera_pass_btn_recuperar_pass);

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

    }

}