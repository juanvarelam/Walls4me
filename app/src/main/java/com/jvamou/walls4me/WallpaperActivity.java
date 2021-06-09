package com.jvamou.walls4me;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.internal.$Gson$Preconditions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class WallpaperActivity extends AppCompatActivity {

    ImageView imgFondo;
    TextView texto;
    ImageButton btnCerrar, btnFavoritos;
    Button btnEstablecerFondo, btnVer, btnMostrarOpciones;

    OutputStream outputStream;

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

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");

        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_imagen_ph)
                .error(R.drawable.ic_imagen_error)
                .into(imgFondo);

        btnCerrar.setVisibility(View.VISIBLE);
        texto.setVisibility(View.VISIBLE);
        btnFavoritos.setVisibility(View.VISIBLE);
        btnMostrarOpciones.setVisibility(View.GONE);

        btnCerrar.setOnClickListener(view -> finish());

        btnFavoritos.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(WallpaperActivity.this);
            builder.setTitle("Esta función estará disponible muy pronto!");
            builder.setPositiveButton("OK", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        btnVer.setOnClickListener(view -> {

            btnCerrar.setVisibility(View.GONE);
            texto.setVisibility(View.GONE);
            btnFavoritos.setVisibility(View.GONE);
            btnEstablecerFondo.setVisibility(View.GONE);
            btnVer.setVisibility(View.GONE);

            Toast.makeText(this, "Toque la imagen para ver las opciones", Toast.LENGTH_SHORT).show();

            imgFondo.setOnClickListener(view1 -> {
                btnCerrar.setVisibility(View.VISIBLE);
                texto.setVisibility(View.VISIBLE);
                btnFavoritos.setVisibility(View.VISIBLE);
                btnEstablecerFondo.setVisibility(View.VISIBLE);
                btnVer.setVisibility(View.VISIBLE);
            });
        });

        btnEstablecerFondo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                establecerFondo();
            }
        });

    }

    public void establecerFondo() {

        Bitmap bitmap = ((BitmapDrawable) imgFondo.getDrawable()).getBitmap();
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());


        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(this, "Fondo de pantalla actualizado!", Toast.LENGTH_SHORT).show();
        }

        catch (IOException e) {
            Toast.makeText(this, "Ha ocurrido un problema", Toast.LENGTH_SHORT).show();
        }


    }



}