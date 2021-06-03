package com.jvamou.walls4me;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class WallpaperActivity extends AppCompatActivity {

    ImageView imgFondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wallpaper);

        imgFondo = findViewById(R.id.act_wallpaper_fondo);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");

        imgFondo.setImageURI(Uri.parse(url));

    }
}