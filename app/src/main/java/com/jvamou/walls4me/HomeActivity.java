package com.jvamou.walls4me;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    //Variables globales fragments bottomNavigation
    FragmentHome fragmentHome = new FragmentHome();
    FragmentCategorias fragmentCategorias = new FragmentCategorias();
    FragmentFavoritos fragmentFavoritos = new FragmentFavoritos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);

        //Listener que recibe el fragment seleccionado por el usuario
        BottomNavigationView btnNavigaton = findViewById(R.id.act_home_botton_navigation);
        btnNavigaton.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        cargarFragment(fragmentHome);
    }

    //Método que le pasa a cargarFragment() el fragment seleccionado
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case R.id.fragment_home:
                    cargarFragment(fragmentHome);
                    return true;
                case R.id.fragment_categorias:
                    cargarFragment(fragmentCategorias);
                    return true;
                case R.id.fragment_favoritos:
                    cargarFragment(fragmentFavoritos);
                    return true;
            }
            return true;
        }
    };

    //Método que reemplaza el fragment existente por el nuevo
    public void cargarFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.act_home_frame_container, fragment);
        transaction.commit();
    }

}