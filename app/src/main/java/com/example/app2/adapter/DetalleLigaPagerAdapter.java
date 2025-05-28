package com.example.app2.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app2.ui.fragments.ClasificacionFragment;
import com.example.app2.ui.fragments.EstadisticasFragment;
import com.example.app2.ui.fragments.PartidosContainerFragment;
import com.example.app2.ui.fragments.GoleadoresFragment;

/**
 * Adapter para ViewPager2 que gestiona los fragments de detalle de liga.
 * Cada página muestra una sección diferente: Clasificación, Partidos o Goleadores.
 *
 * - Recibe el id de la liga y la temporada para pasar a cada fragment.
 * - Utiliza FragmentStateAdapter para gestionar los fragments de manera eficiente.
 */
public class DetalleLigaPagerAdapter extends FragmentStateAdapter {

    // Identificador de la liga y temporada seleccionada
    private final int leagueId;
    private final int season;

    /**
     * Constructor del adapter.
     * @param fragmentActivity Actividad que contiene el ViewPager2.
     * @param leagueId ID de la liga a mostrar.
     * @param season Temporada seleccionada.
     */
    public DetalleLigaPagerAdapter(@NonNull FragmentActivity fragmentActivity, int leagueId, int season) {
        super(fragmentActivity);
        this.leagueId = leagueId;
        this.season = season;
    }

    /**
     * Devuelve el fragment correspondiente a la posición indicada.
     * @param position Posición de la página (0: Clasificación, 1: Partidos, 2: Goleadores).
     * @return Fragment correspondiente.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                // Fragmento de clasificación de la liga
                return ClasificacionFragment.newInstance(leagueId, season);

            case 1:
                // Fragmento de partidos de la liga
                return PartidosContainerFragment.newInstance(leagueId, season);

            case 2:
                // Fragmento de goleadores de la liga
                return GoleadoresFragment.newInstance(leagueId, season);

            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    /**
     * Devuelve el número total de páginas/fragments gestionados por el adapter.
     * @return Número de páginas (3).
     */
    @Override
    public int getItemCount() {
        return 3;
    }
}