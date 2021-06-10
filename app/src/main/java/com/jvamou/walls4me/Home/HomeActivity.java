package com.jvamou.walls4me.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jvamou.walls4me.R;

public class HomeActivity extends AppCompatActivity {

    //Variables globales fragments bottomNavigation
    Fragment fragmentInicio, fragmentCategorias, fragmentFavoritos, fragmentAjustes;

    BottomNavigationView btnNavigaton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);

        fragmentInicio = new FragmentInicio();
        fragmentCategorias = new FragmentCategorias();
        fragmentFavoritos = new FragmentFavoritos();
        fragmentAjustes = new FragmentInfo();

        //Listener que recibe el fragment seleccionado por el usuario
        btnNavigaton = findViewById(R.id.act_home_botton_navigation);
        btnNavigaton.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        cargarFragment(fragmentInicio);

    }

    //Método que le pasa a cargarFragment() el fragment seleccionado
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case R.id.fragment_inicio:
                    cargarFragment(fragmentInicio);
                    return true;
                case R.id.fragment_categorias:
                    cargarFragment(fragmentCategorias);
                    return true;
                case R.id.fragment_favoritos:
                    cargarFragment(fragmentFavoritos);
                    return true;
                case R.id.fragment_info:
                    cargarFragment(fragmentAjustes);
                    return true;
            }
            return true;
        }
    };

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
    public void cargarFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.act_home_layout_container, fragment);
        transaction.commit();
    }

}