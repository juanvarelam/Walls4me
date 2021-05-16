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

    }
}