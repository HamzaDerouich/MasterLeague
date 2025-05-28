package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.app2.ui.activities.AjustesActivity;
import com.example.app2.ui.fragments.AjustesFragment;
import com.example.app2.ui.fragments.GoleadoresFragment;
import com.example.app2.ui.fragments.LigasFragment;
import com.example.app2.ui.fragments.NoticiasFragment;
import com.example.app2.ui.fragments.PartidosFragment;
import com.example.app2.model.UsuarioModel;
import com.example.app2.ui.fragments.SecctionGoleadoresFragment;
import com.example.app2.ui.fragments.FavoritosFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.Objects;

/**
 * Actividad principal de la aplicación MasterLeague.
 *
 * Funcionalidades principales:
 * - Gestiona la navegación principal mediante DrawerLayout (menú lateral) y BottomNavigationView (barra inferior).
 * - Permite cambiar entre los principales fragmentos de la app: Ligas, Partidos, Noticias, Goleadores, Favoritos y Ajustes.
 * - Muestra la barra de búsqueda en el toolbar para buscar ligas, equipos, etc.
 * - Recibe el usuario autenticado por intent y lo pasa a los fragmentos que lo requieren.
 * - Sincroniza la selección entre el menú lateral y la barra inferior.
 * - Gestiona el ciclo de vida de los fragmentos y la navegación hacia atrás.
 *
 * Uso típico:
 * - Es la pantalla principal tras iniciar sesión.
 * - El usuario puede navegar entre las diferentes secciones de la app y buscar contenido.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNav;
    private MaterialToolbar toolbar;
    private SearchView searchView;
    private UsuarioModel usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNav = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.barrabusqueda);

        usuario = (UsuarioModel) getIntent().getSerializableExtra("usuario");

        // Toolbar
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Fragment inicial
        if (savedInstanceState == null) {
            loadFragment(new LigasFragment());
            navigationView.setCheckedItem(R.id.nav_ligas);
            bottomNav.setSelectedItemId(R.id.nav_ligas);
        }

        navigationView.setNavigationItemSelectedListener(this);
        bottomNav.setOnNavigationItemSelectedListener(this::onBottomNavItemSelected);
    }

    /**
     * Maneja la selección de la barra de navegación inferior.
     */
    private boolean onBottomNavItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bottom_nav_partidos) {
            AjustesFragment ajustesFragment = new AjustesFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", (Serializable) usuario);
            ajustesFragment.setArguments(bundle);
            loadFragment(new PartidosFragment());
        } else if (id == R.id.bottom_nav_ligas) {
            loadFragment(new LigasFragment());
        } else if (id == R.id.bottom_nav_noticias) {
            loadFragment(new NoticiasFragment());
        } else if (id == R.id.bottom_nav_goleadores) {
            loadFragment(new SecctionGoleadoresFragment());
        } else if (id == R.id.bottom_nav_salir) {
            finish();
        }

        return true;
    }

    /**
     * Maneja la selección de elementos del menú lateral (Drawer).
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_ligas) {
            loadFragment(new LigasFragment());
            bottomNav.setSelectedItemId(R.id.nav_ligas);
        } else if (id == R.id.nav_ajustes) {
            AjustesFragment ajustesFragment = new AjustesFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("usuario", (Parcelable) usuario);
            ajustesFragment.setArguments(bundle);

            loadFragment(ajustesFragment);

        } else if (id == R.id.nav_salir) {
            finish();
        } else if (id == R.id.nav_partidos) {
            loadFragment(new PartidosFragment());
            bottomNav.setSelectedItemId(R.id.bottom_nav_partidos);
        } else if (id == R.id.nav_noticias) {
            loadFragment(new NoticiasFragment());
            bottomNav.setSelectedItemId(R.id.bottom_nav_noticias);
        } else if (id == R.id.nav_goleadores) {
            loadFragment(new SecctionGoleadoresFragment());
            bottomNav.setSelectedItemId(R.id.bottom_nav_goleadores);
        } else if (id == R.id.nav_favoritos) {
            loadFragment(new FavoritosFragment());
            // Si tienes un BottomNavigationView para favoritos, selecciona el ítem correspondiente aquí
            // bottomNav.setSelectedItemId(R.id.bottom_nav_favoritos);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Reemplaza el fragmento actual por el proporcionado.
     */
    private void loadFragment(androidx.fragment.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, fragment)
                .commit();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Lógica al buscar
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Lógica de búsqueda en tiempo real
        return false;
    }

    /**
     * Gestiona el comportamiento al pulsar el botón de retroceso.
     * Cierra el Drawer o la barra de búsqueda si están abiertos, o sale de la app.
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (searchView != null && !searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }
}