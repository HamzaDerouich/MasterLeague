package com.example.app2.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app2.ui.fragments.PartidosPasadosFragment;
import com.example.app2.ui.fragments.PartidosProximosFragment;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Adapter para ViewPager2 que gestiona los fragments de partidos (próximos y pasados) de una liga.
 *
 * - Recibe el id de la liga y la temporada para pasar a cada fragment.
 * - Utiliza FragmentStateAdapter para gestionar los fragments de manera eficiente.
 * - Cada página muestra una sección diferente: Próximos partidos o Partidos pasados.
 */
public class PartidosPagerAdapter extends FragmentStateAdapter {

    private final int leagueId;
    private final int season;

    /**
     * Constructor del adapter.
     * @param fragment Fragment padre que contiene el ViewPager2.
     * @param leagueId ID de la liga a mostrar.
     * @param season Temporada seleccionada.
     */
    public PartidosPagerAdapter(@NonNull Fragment fragment, int leagueId, int season) {
        super(fragment.getChildFragmentManager(), fragment.getLifecycle());
        this.leagueId = leagueId;
        this.season = season;
    }

    /**
     * Devuelve el fragment correspondiente a la posición indicada.
     * @param position Posición de la página (0: Próximos partidos, 1: Partidos pasados).
     * @return Fragment correspondiente.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            // Fragmento de próximos partidos
            return PartidosProximosFragment.newInstance(leagueId, season);
        } else {
            // Fragmento de partidos pasados
            return PartidosPasadosFragment.newInstance(leagueId, season);
        }
    }

    /**
     * Devuelve el número total de páginas/fragments gestionados por el adapter.
     * @return Número de páginas (2: Próximos y Pasados).
     */
    @Override
    public int getItemCount() {
        return 2; // Próximos y Pasados
    }
}