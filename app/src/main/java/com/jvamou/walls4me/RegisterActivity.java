package com.jvamou.walls4me;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    //variables para datosd e registro
    private EditText authEmail;
    private EditText authPassword;
    private EditText authPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);

        //referencia a los elementos
        authEmail = findViewById(R.id.act_register_txt_email);
        authPassword = findViewById(R.id.act_register_txt_password);
        authPasswordConfirm = findViewById(R.id.act_register_txt_password_confirm);
    }
}