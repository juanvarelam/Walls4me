package com.jvamou.walls4me;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

public class WallpaperActivity extends AppCompatActivity {

    ImageView imgFondo;
    ImageButton btnCerrar;
    Button btnEstablecerFondo;
    Button btnDescargar;
    LinearLayout layoutSuperior;
    LinearLayout layoutInferior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wallpaper);

        imgFondo = findViewById(R.id.act_wallpaper_fondo);
        btnCerrar = findViewById(R.id.act_wallpaper_btn_cerrar);
        btnEstablecerFondo = findViewById(R.id.act_wallpaper_btn_establecer_fondo);
        btnDescargar = findViewById(R.id.act_wallpaper_btn_descargar);
        layoutSuperior = findViewById(R.id.act_wallpaper_layout_superior);
        layoutInferior = findViewById(R.id.act_wallpaper_layout_inferior);


        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");

        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_imagen_ph)
                .error(R.drawable.ic_imagen_error)
                .into(imgFondo);
    }
}