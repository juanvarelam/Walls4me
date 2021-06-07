package com.jvamou.walls4me;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.File;
import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {

    ImageView imgFondo;
    TextView texto;
    ImageButton btnCerrar, btnFavoritos;
    Button btnEstablecerFondo, btnVer, btnDescargar, btnMostrarOpciones;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wallpaper);

        imgFondo = findViewById(R.id.act_wallpaper_fondo);
        btnCerrar = findViewById(R.id.act_wallpaper_btn_cerrar);
        texto = findViewById(R.id.act_wallpaper_texto);
        btnFavoritos = findViewById(R.id.act_wallpaper_btn_favorito);
        btnEstablecerFondo = findViewById(R.id.act_wallpaper_btn_establecer_fondo);
        btnVer = findViewById(R.id.act_wallpaper_btn_ver_wallpaper);
        btnDescargar = findViewById(R.id.act_wallpaper_btn_descargar);
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
            btnDescargar.setVisibility(View.GONE);

            new CountDownTimer(3000, 1000) {

                public void onTick(long millisUntilFinished) {
                    btnMostrarOpciones.setVisibility(View.VISIBLE);
                }

                public void onFinish() {

                    btnMostrarOpciones.setVisibility(View.GONE);
                }

            }.start();

            imgFondo.setOnClickListener(view1 -> {
                btnCerrar.setVisibility(View.VISIBLE);
                texto.setVisibility(View.VISIBLE);
                btnFavoritos.setVisibility(View.VISIBLE);
                btnEstablecerFondo.setVisibility(View.VISIBLE);
                btnVer.setVisibility(View.VISIBLE);
                btnDescargar.setVisibility(View.VISIBLE);
                btnMostrarOpciones.setVisibility(View.GONE);
            });
        });

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallpaperActivity.this);

        btnEstablecerFondo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                establecerFondo();
            }
        });

    }

    public void establecerFondo() {

        /*Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");*/

        Bitmap bitmap = ((BitmapDrawable) imgFondo.getDrawable()).getBitmap();
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Integer.parseInt(url));
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());


        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(this, "Fondo de pantalla actualizado", Toast.LENGTH_LONG).show();
        }

        catch (IOException e) {
            Toast.makeText(this, "Ha ocurrido un problema", Toast.LENGTH_SHORT).show();
        }


    }
}