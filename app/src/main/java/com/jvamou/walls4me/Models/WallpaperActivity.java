package com.jvamou.walls4me.Models;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jvamou.walls4me.R;

import java.io.IOException;
import java.io.OutputStream;

public class WallpaperActivity extends AppCompatActivity {

    //Vars globales
    ImageView imgFondo;
    TextView texto;
    ImageButton btnCerrar, btnFavoritos;
    Button btnEstablecerFondo, btnVer, btnMostrarOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wallpaper);

        imgFondo = findViewById(R.id.act_wallpaper_fondo);
        btnCerrar = findViewById(R.id.act_wallpaper_btn_cerrar);
        texto = findViewById(R.id.act_wallpaper_texto);
        btnFavoritos = findViewById(R.id.act_wallpaper_btn_favorito);
        btnEstablecerFondo = findViewById(R.id.act_wallpaper_btn_establecer_fondo);
        btnVer = findViewById(R.id.act_wallpaper_btn_visualizar);
        btnMostrarOpciones = findViewById(R.id.act_wallpaper_btn_mostrar_opciones);

        //Se obtiene la url de la imagen a mostrar que llega en el Bundle
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");

        //Librería Glide que carga las imágenes en -> into()
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_imagen_ph)
                .error(R.drawable.ic_imagen_ph)
                .into(imgFondo);

        //Por defecto se establece esta visibilidad a los componentes
        btnCerrar.setVisibility(View.VISIBLE);
        texto.setVisibility(View.VISIBLE);
        btnFavoritos.setVisibility(View.VISIBLE);
        btnMostrarOpciones.setVisibility(View.GONE);

        //Listener que cierra el acticity actual y vuelve al anterior
        btnCerrar.setOnClickListener(view -> finish());

        //Listener que muestra cuadro de diálogo al pulsar en botón favoritos
        btnFavoritos.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(WallpaperActivity.this);
            builder.setTitle("Esta función estará disponible muy pronto!");
            builder.setPositiveButton("OK", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        //Listener que oculta los componentes para ver la imagen completa
        btnVer.setOnClickListener(view -> {

            btnCerrar.setVisibility(View.GONE);
            texto.setVisibility(View.GONE);
            btnFavoritos.setVisibility(View.GONE);
            btnEstablecerFondo.setVisibility(View.GONE);
            btnVer.setVisibility(View.GONE);

            Toast.makeText(this, "Toque la imagen para ver las opciones", Toast.LENGTH_SHORT).show();

            //Listener que hace visible todos los componentes al hacer click en la imagen
            imgFondo.setOnClickListener(view1 -> {
                btnCerrar.setVisibility(View.VISIBLE);
                texto.setVisibility(View.VISIBLE);
                btnFavoritos.setVisibility(View.VISIBLE);
                btnEstablecerFondo.setVisibility(View.VISIBLE);
                btnVer.setVisibility(View.VISIBLE);
            });
        });

        //Listener que llama al método establecerFondo()
        btnEstablecerFondo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                establecerFondo();
            }
        });

    }

    public void establecerFondo() {

        //Se crea un Bitmap a partir de la imagen de fondo
        Bitmap bitmap = ((BitmapDrawable) imgFondo.getDrawable()).getBitmap();
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());


        try {
            wallpaperManager.setBitmap(bitmap);
            String msg = getApplicationContext().getResources().getString(R.string.act_wallpaper_fondo_actualizado);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

        catch (IOException e) {
            String msg = getApplicationContext().getResources().getString(R.string.act_wallpaper_problema);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }


    }



}