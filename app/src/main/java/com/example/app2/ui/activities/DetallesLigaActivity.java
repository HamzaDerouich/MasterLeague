package com.example.app2.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.app2.R;
import com.example.app2.adapter.ClasificacionAdapter;
import com.example.app2.adapter.DetalleLigaPagerAdapter;
import com.example.app2.adapter.GoleadoresAdapter;
import com.example.app2.adapter.PartidosAdapter;
import com.example.app2.api.FootballDataService;
import com.example.app2.api.footballDataServiceInterfaces.ClasificacionCallBack;
import com.example.app2.api.footballDataServiceInterfaces.MaximosGoleadoresCallBack;
import com.example.app2.api.footballDataServiceInterfaces.ResultadosPasadosCallBack;
import com.example.app2.model.ClasificacionModel;
import com.example.app2.model.FavoritoLigaModel;
import com.example.app2.model.GoleadorModel;
import com.example.app2.model.LigaModel;
import com.example.app2.model.PartidoResultadoModel;
import com.example.app2.data.FavoritosRepository;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Actividad que muestra el detalle de una liga seleccionada, incluyendo:
 * - Clasificación de equipos
 * - Partidos recientes y próximos
 * - Máximos goleadores
 * - Gestión de favoritos
 * 
 * Funcionalidades principales:
 * - Obtiene los datos de la liga seleccionada y los muestra en diferentes pestañas.
 * - Permite añadir o eliminar la liga de favoritos mediante FloatingActionButton.
 * - Usa ViewPager2 y TabLayout para navegar entre pestañas (Clasificación, Partidos, Estadísticas).
 * - Muestra los datos usando RecyclerViews y Adapters personalizados.
 * - Gestiona la carga asíncrona de datos desde la API usando FootballDataService.
 * - Muestra mensajes de error y estados de carga.
 * - Maneja el ciclo de vida y la limpieza de recursos.
 */
public class DetallesLigaActivity extends AppCompatActivity {

    // Constantes para logs y control de reintentos
    private static final String TAG = "DetallesLigaActivity";
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 2000;

    // Servicios y modelos
    private FootballDataService footballDataService;
    private LigaModel ligaSeleccionada;
    private int season = 2023;
    private int retryCount = 0;

    // Vistas principales
    private ImageView leagueLogo;
    private TextView leagueName;
    private TextView leagueSeason;
    private CollapsingToolbarLayout collapsingToolbar;
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    private View mainContent;

    // RecyclerViews para mostrar datos
    private RecyclerView standingsRecycler;
    private RecyclerView recentMatchesRecycler;
    private RecyclerView upcomingMatchesRecycler;
    private RecyclerView topScorersRecycler;

    // Adapters para los RecyclerViews
    private ClasificacionAdapter clasificacionAdapter;
    private PartidosAdapter partidosPasadosAdapter;
    private PartidosAdapter partidosProximosAdapter;
    private GoleadoresAdapter goleadoresAdapter;

    // ViewPager y TabLayout para pestañas
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    // Executor para tareas asíncronas
    private ExecutorService executorService;

    // Variables para rastrear el estado de carga de cada sección
    private boolean standingsLoaded = false;
    private boolean pastMatchesLoaded = false;
    private boolean upcomingMatchesLoaded = false;
    private boolean topScorersLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalles_liga);

        try {
            // Ajusta el padding para la barra de sistema
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            // Inicializa servicios y obtiene la liga seleccionada
            footballDataService = new FootballDataService(this);
            executorService = Executors.newSingleThreadExecutor();
            ligaSeleccionada = getIntent().getParcelableExtra("liga");
            if (ligaSeleccionada == null) {
                showErrorAndFinish("Error al cargar los datos de la liga");
                return;
            }

            // Inicializa vistas y componentes
            initViews();
            setupFAB();
            setupRemoveFavFAB();
            actualizarFABs();
            showLoading(true);
            setupToolbar();
            setupViewPager();

            // Carga los datos de la liga
            loadData();
        } catch (Exception e) {
            Log.e(TAG, "Error en onCreate", e);
            showErrorAndFinish("Error inesperado al iniciar la actividad");
        }
    }

    /**
     * Inicializa las vistas y componentes de la interfaz.
     */
    private void initViews() {
        try {
            leagueLogo = findViewById(R.id.league_logo);
            leagueName = findViewById(R.id.league_name);
            leagueSeason = findViewById(R.id.league_season);
            collapsingToolbar = findViewById(R.id.collapsing_toolbar);
            fab = findViewById(R.id.fab);
            progressBar = findViewById(R.id.progress_bar);
            mainContent = findViewById(R.id.main);

            standingsRecycler = findViewById(R.id.standings_recycler);
            recentMatchesRecycler = findViewById(R.id.recent_matches_recycler);
            upcomingMatchesRecycler = findViewById(R.id.upcoming_matches_recycler);
            topScorersRecycler = findViewById(R.id.top_scorers_recycler);

            viewPager = findViewById(R.id.view_pager);
            tabLayout = findViewById(R.id.tab_layout);
        } catch (Exception e) {
            Log.e(TAG, "Error inicializando vistas", e);
            showErrorAndFinish("Error al inicializar la interfaz");
        }
    }

    /**
     * Configura la barra de herramientas y muestra los datos de la liga.
     */
    private void setupToolbar() {
        try {
            setSupportActionBar(findViewById(R.id.toolbar));
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("");
            }
            leagueName.setText(ligaSeleccionada.getName());
            leagueSeason.setText(String.format("%d/%d Season", season, season + 1));
            Glide.with(this)
                    .load(ligaSeleccionada.getLogo())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_league)
                    .error(R.drawable.ic_league)
                    .into(leagueLogo);
            collapsingToolbar.setTitle(ligaSeleccionada.getName());
        } catch (Exception e) {
            Log.e(TAG, "Error configurando toolbar", e);
        }
    }

    /**
     * Configura el ViewPager y las pestañas para navegar entre secciones.
     */
    private void setupViewPager() {
        try {
            DetalleLigaPagerAdapter pagerAdapter = new DetalleLigaPagerAdapter(
                    this,
                    ligaSeleccionada.getId(),
                    season
            );
            viewPager.setAdapter(pagerAdapter);
            viewPager.setOffscreenPageLimit(2);

            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                switch (position) {
                    case 0: tab.setText("Clasificación"); break;
                    case 1: tab.setText("Partidos"); break;
                    case 2: tab.setText("Estadísticas"); break;
                }
            }).attach();
        } catch (Exception e) {
            Log.e(TAG, "Error configurando ViewPager", e);
            showSnackbar("Error al configurar las pestañas");
        }
    }

    /**
     * Configura el botón flotante para añadir la liga a favoritos.
     */
    private void setupFAB() {
        fab.setOnClickListener(v -> {
            FavoritosRepository repo = new FavoritosRepository(this);
            FavoritoLigaModel favorito = new FavoritoLigaModel(
                    ligaSeleccionada.getId(),
                    ligaSeleccionada.getName(),
                    ligaSeleccionada.getLogo(),
                    season
            );
            boolean exito = repo.agregarFavorito(favorito);
            if (exito) {
                Snackbar.make(fab, "Liga añadida a favoritos", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(fab, "La liga ya está en favoritos", Snackbar.LENGTH_SHORT).show();
            }
            actualizarFABs();
        });
    }

    /**
     * Configura el botón flotante para eliminar la liga de favoritos.
     */
    private void setupRemoveFavFAB() {
        FloatingActionButton fabRemove = findViewById(R.id.fab_remove_fav);
        fabRemove.setOnClickListener(v -> {
            FavoritosRepository repo = new FavoritosRepository(this);
            boolean exito = repo.eliminarFavoritoPorLigaId(ligaSeleccionada.getId());
            if (exito) {
                Snackbar.make(fabRemove, "Liga eliminada de favoritos", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(fabRemove, "No se pudo eliminar de favoritos", Snackbar.LENGTH_SHORT).show();
            }
            actualizarFABs();
        });
    }

    /**
     * Actualiza la visibilidad de los botones de favoritos según el estado actual.
     */
    private void actualizarFABs() {
        FavoritosRepository repo = new FavoritosRepository(this);
        boolean esFavorito = repo.esFavorito(ligaSeleccionada.getId());
        fab.setVisibility(esFavorito ? View.GONE : View.VISIBLE);
        findViewById(R.id.fab_remove_fav).setVisibility(esFavorito ? View.VISIBLE : View.GONE);
    }

    /**
     * Inicia la carga de datos de la liga (clasificación, partidos, goleadores).
     */
    private void loadData() {
        if (executorService == null || executorService.isShutdown()) {
            executorService = Executors.newSingleThreadExecutor();
        }
        executorService.execute(() -> {
            runOnUiThread(this::setupAdapters);
            getStandings();
            getPastMatches();
            getUpcomingMatches();
            getTopScorers();
        });
    }

    /**
     * Configura los adapters de los RecyclerViews.
     */
    private void setupAdapters() {
        try {
            clasificacionAdapter = new ClasificacionAdapter(new ArrayList<>());
            standingsRecycler.setHasFixedSize(true);
            standingsRecycler.setLayoutManager(new LinearLayoutManager(this));
            standingsRecycler.setAdapter(clasificacionAdapter);

            partidosPasadosAdapter = new PartidosAdapter(new ArrayList<>(), true);
            recentMatchesRecycler.setHasFixedSize(true);
            recentMatchesRecycler.setLayoutManager(new LinearLayoutManager(this));
            recentMatchesRecycler.setAdapter(partidosPasadosAdapter);

            partidosProximosAdapter = new PartidosAdapter(new ArrayList<>(), false);
            upcomingMatchesRecycler.setHasFixedSize(true);
            upcomingMatchesRecycler.setLayoutManager(new LinearLayoutManager(this));
            upcomingMatchesRecycler.setAdapter(partidosProximosAdapter);

            goleadoresAdapter = new GoleadoresAdapter(new ArrayList<>());
            topScorersRecycler.setHasFixedSize(true);
            topScorersRecycler.setLayoutManager(new LinearLayoutManager(this));
            topScorersRecycler.setAdapter(goleadoresAdapter);

        } catch (Exception e) {
            Log.e(TAG, "Error configurando adapters", e);
            showSnackbar("Error al configurar la interfaz");
        }
    }

    /**
     * Muestra un mensaje de error y finaliza la actividad.
     * @param message Mensaje a mostrar.
     */
    private void showErrorAndFinish(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Muestra un mensaje Snackbar en la pantalla.
     * @param message Mensaje a mostrar.
     */
    private void showSnackbar(String message) {
        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Muestra u oculta el indicador de carga.
     * @param loading true para mostrar el loading, false para mostrar el contenido.
     */
    private void showLoading(boolean loading) {
        if (progressBar != null && mainContent != null) {
            progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
            mainContent.setVisibility(loading ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Obtiene la clasificación de la liga desde la API.
     */
    private void getStandings() {
        if (footballDataService == null || ligaSeleccionada == null) {
            Log.e(TAG, "Servicio o liga no inicializada");
            return;
        }
        footballDataService.obtenerClasificacion(ligaSeleccionada.getId(), season, new ClasificacionCallBack() {
            @Override
            public void onSuccess(ArrayList<ClasificacionModel> clasificacionModelArrayList) {
                runOnUiThread(() -> {
                    if (clasificacionAdapter != null) {
                        clasificacionAdapter.updateData(clasificacionModelArrayList);
                    }
                    standingsLoaded = true;
                    checkDataLoaded();
                });
            }
            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Error obteniendo clasificación: " + errorMessage);
                showSnackbar("Error al obtener la clasificación");
                standingsLoaded = true;
                checkDataLoaded();
            }
        });
    }

    /**
     * Obtiene los partidos pasados de la liga desde la API.
     */
    private void getPastMatches() {
        if (footballDataService == null || ligaSeleccionada == null) {
            Log.e(TAG, "Servicio o liga no inicializada");
            return;
        }
        footballDataService.obtenerResultadosPasados(ligaSeleccionada.getId(), season, new ResultadosPasadosCallBack() {
            @Override
            public void onSuccess(List<PartidoResultadoModel> resultados) {
                runOnUiThread(() -> {
                    if (partidosPasadosAdapter != null) {
                        partidosPasadosAdapter.updateData(resultados);
                    }
                    pastMatchesLoaded = true;
                    checkDataLoaded();
                });
            }
            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Error obteniendo resultados pasados: " + errorMessage);
                showSnackbar("Error al obtener resultados pasados");
                pastMatchesLoaded = true;
                checkDataLoaded();
            }
        });
    }

    /**
     * Obtiene los próximos partidos de la liga desde la API.
     */
    private void getUpcomingMatches() {
        if (footballDataService == null || ligaSeleccionada == null) {
            Log.e(TAG, "Servicio o liga no inicializada");
            return;
        }
        footballDataService.obtenerProximosPartidos(ligaSeleccionada.getId(), season, new ResultadosPasadosCallBack() {
            @Override
            public void onSuccess(List<PartidoResultadoModel> resultados) {
                runOnUiThread(() -> {
                    if (partidosProximosAdapter != null) {
                        partidosProximosAdapter.updateData(resultados);
                    }
                    upcomingMatchesLoaded = true;
                    checkDataLoaded();
                });
            }
            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Error obteniendo próximos partidos: " + errorMessage);
                showSnackbar("Error al obtener próximos partidos");
                upcomingMatchesLoaded = true;
                checkDataLoaded();
            }
        });
    }

    /**
     * Obtiene los máximos goleadores de la liga desde la API.
     */
    private void getTopScorers() {
        if (footballDataService == null || ligaSeleccionada == null) {
            Log.e(TAG, "Servicio o liga no inicializada");
            return;
        }
        footballDataService.obtenerMaximosGoleadores(ligaSeleccionada.getId(), season, new MaximosGoleadoresCallBack() {
            @Override
            public void onSuccess(ArrayList<GoleadorModel> goleadorModelArrayList) {
                runOnUiThread(() -> {
                    if (goleadoresAdapter != null) {
                        goleadoresAdapter.updateData(goleadorModelArrayList);
                    }
                    topScorersLoaded = true;
                    checkDataLoaded();
                });
            }
            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Error obteniendo goleadores: " + errorMessage);
                showSnackbar("Error al obtener goleadores");
                topScorersLoaded = true;
                checkDataLoaded();
            }
        });
    }

    /**
     * Verifica si todos los datos han sido cargados para mostrar el contenido.
     */
    private void checkDataLoaded() {
        if (standingsLoaded && pastMatchesLoaded && upcomingMatchesLoaded && topScorersLoaded) {
            runOnUiThread(() -> showLoading(false));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
        if (footballDataService != null) {
            footballDataService.cancelPendingRequests();
        }
        if (!isFinishing() && !isDestroyed()) {
            Glide.with(this).clear(leagueLogo);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}