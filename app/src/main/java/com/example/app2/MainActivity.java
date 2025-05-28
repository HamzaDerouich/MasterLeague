package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.app2.Models.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Elementos

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.barrabusqueda);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        setSupportActionBar(toolbar);

        // Configuración del DrawerToggle

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Al abrir la app, mostrar el fragmento de Clasificación

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new Ligas()).commit();
            navigationView.setCheckedItem(R.id.Ligas);
        }

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if( item.getItemId() == R.id.Partidos )
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new Partidos()).commit();

            }
            else if(  item.getItemId() == R.id.Ligas )
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new Ligas()).commit();

            }
            else if(  item.getItemId() == R.id.Noticias )
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new NoticiasInfo()).commit();

            }
            else if(  item.getItemId() == R.id.Goleadores )
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new Goleadores()).commit();

            }
            else if(  item.getItemId() == R.id.Salir )
            {
                finish();
            }

            return true;
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Ligas) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new Ligas()).commit();
        } else if (item.getItemId() == R.id.Goleadores) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new Goleadores()).commit();
        } else if (item.getItemId() == R.id.Partidos) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new Partidos()).commit();
        } else if (item.getItemId() == R.id.salir) {
            finish();
        } else if ( item.getItemId() == R.id.Ajustes)
        {
            Intent intent = new Intent(getApplicationContext(), Ajustes.class);
            intent.putExtra("usuario",usuario);
            startActivity(intent);
        }

        // Marcar el ítem seleccionado

        navigationView.setCheckedItem(item.getItemId());

        // Cerrar el Drawer después de seleccionar el ítem

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
