package com.example.app2.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app2.R;
import com.example.app2.adapter.LigasCarouselAdapter;
import com.example.app2.adapter.PartidosPagerAdapter;
import com.example.app2.api.FootballDataService;
import com.example.app2.api.footballDataServiceInterfaces.LigasCallback;
import com.example.app2.model.LigaModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

/**
 * Fragmento que muestra los partidos de una liga seleccionada, organizados en pestañas de "Próximos" y "Pasados".
 *
 * Funcionalidades principales:
 * - Solicita la lista de ligas a la API usando FootballDataService y las muestra en un carrusel horizontal (RecyclerView).
 * - Permite al usuario seleccionar una liga del carrusel para ver sus partidos.
 * - Muestra los partidos de la liga seleccionada en dos pestañas ("Próximos" y "Pasados") usando ViewPager2 y TabLayout.
 * - Utiliza PartidosPagerAdapter para gestionar los fragmentos hijos de partidos próximos y pasados.
 * - Muestra un ProgressBar mientras se cargan las ligas.
 * - Gestiona errores mostrando mensajes mediante Toast.
 *
 * Uso típico:
 * - Se utiliza en la sección de partidos de la app para navegar entre diferentes ligas y consultar sus partidos próximos y pasados.
 * - El usuario puede cambiar de liga usando el carrusel y ver los partidos organizados por estado.
 */
public class PartidosFragment extends Fragment {

    private int leagueId = 61; // Por defecto LaLiga
    private int season = 2023; // Valor por defecto

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ProgressBar progressBar;
    private RecyclerView rvLigas;
    private LigasCarouselAdapter ligasAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partidos, container, false);

        rvLigas = view.findViewById(R.id.rv_ligas);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        progressBar = view.findViewById(R.id.progress_bar);

        // Configurar RecyclerView para el carrusel de ligas
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rvLigas.setLayoutManager(layoutManager);

        ligasAdapter = new LigasCarouselAdapter(requireContext(), liga -> {
            leagueId = liga.getId();
            setupViewPager();
        });
        rvLigas.setAdapter(ligasAdapter);

        cargarLigas();

        return view;
    }

    /**
     * Solicita la lista de ligas a la API y actualiza el carrusel.
     */
    private void cargarLigas() {
        progressBar.setVisibility(View.VISIBLE);
        FootballDataService service = new FootballDataService(requireContext());
        service.obtenerLigas(new LigasCallback() {
            @Override
            public void onSuccess(ArrayList<LigaModel> ligas) {
                requireActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    ligasAdapter.setLigas(ligas);

                    // Selecciona la primera liga por defecto
                    if (!ligas.isEmpty()) {
                        leagueId = ligas.get(0).getId();
                        setupViewPager();
                    }
                });
            }

            @Override
            public void onError(String error) {
                requireActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Error al cargar ligas: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    /**
     * Configura el ViewPager2 y TabLayout para mostrar los partidos próximos y pasados de la liga seleccionada.
     */
    private void setupViewPager() {
        PartidosPagerAdapter pagerAdapter = new PartidosPagerAdapter(this, leagueId, season);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("Próximos");
            else tab.setText("Pasados");
        }).attach();
    }
}