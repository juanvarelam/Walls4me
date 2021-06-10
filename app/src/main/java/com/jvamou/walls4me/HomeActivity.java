package com.jvamou.walls4me;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView btnNavigaton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);

        cargarFragment(new FragmentInicio());

        //Listener que recibe el fragment seleccionado por el usuario
        btnNavigaton = findViewById(R.id.act_home_botton_navigation);
        btnNavigaton.setOnNavigationItemSelectedListener(this);
    }

    //Método que le pasa a cargarFragment() el fragment seleccionado
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch(item.getItemId()) {
            case R.id.fragment_inicio:
                fragment = new FragmentInicio();
                break;
            case R.id.fragment_categorias:
                fragment = new FragmentCategorias();
                break;
            case R.id.fragment_favoritos:
                fragment = new FragmentFavoritos();
                break;
            case R.id.fragment_info:
                fragment = new FragmentInfo();
                break;
        }

        return cargarFragment(fragment);
    }

    @Override
    public void onBackPressed() {

        if(btnNavigaton.getSelectedItemId() == R.id.fragment_inicio) {
            super.onBackPressed();
            finish();  //se sale de la app
        }else{
            btnNavigaton.setSelectedItemId(R.id.fragment_inicio);
        }

    }


    //Método que reemplaza el fragment existente por el nuevo
    public boolean cargarFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.act_home_layout_container, fragment);
        transaction.commit();

        return true;
    }


}