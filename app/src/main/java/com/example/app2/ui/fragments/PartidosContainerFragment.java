package com.example.app2.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app2.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Fragmento contenedor que muestra dos pestañas: partidos próximos y partidos pasados de una liga.
 *
 * Funcionalidades principales:
 * - Recibe el ID de la liga y la temporada como argumentos.
 * - Utiliza un ViewPager2 y TabLayout para mostrar dos fragmentos hijos:
 *      - PartidosProximosFragment: muestra los próximos partidos de la liga.
 *      - PartidosPasadosFragment: muestra los partidos ya jugados de la liga.
 * - Permite al usuario navegar entre las pestañas "Próximos" y "Pasados".
 *
 * Uso típico:
 * - Se utiliza dentro de la pantalla de detalles de una liga para mostrar los partidos organizados por estado.
 * - Se instancia usando el método estático newInstance(int leagueId, int season).
 */
public class PartidosContainerFragment extends Fragment {

    private static final String ARG_LEAGUE_ID = "league_id";
    private static final String ARG_SEASON = "season";

    /**
     * Crea una nueva instancia del fragmento con los argumentos necesarios.
     * @param leagueId ID de la liga.
     * @param season Temporada.
     * @return Instancia de PartidosContainerFragment.
     */
    public static PartidosContainerFragment newInstance(int leagueId, int season) {
        PartidosContainerFragment fragment = new PartidosContainerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LEAGUE_ID, leagueId);
        args.putInt(ARG_SEASON, season);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_partidos_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int leagueId = getArguments().getInt(ARG_LEAGUE_ID);
        int season = getArguments().getInt(ARG_SEASON);

        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        PartidosPagerAdapter pagerAdapter = new PartidosPagerAdapter(this, leagueId, season);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(position == 0 ? "Próximos" : "Pasados");
        }).attach();
    }

    /**
     * Adaptador para gestionar los fragmentos de partidos próximos y pasados en el ViewPager2.
     */
    private static class PartidosPagerAdapter extends FragmentStateAdapter {
        private final int leagueId;
        private final int season;

        public PartidosPagerAdapter(@NonNull Fragment fragment, int leagueId, int season) {
            super(fragment);
            this.leagueId = leagueId;
            this.season = season;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return PartidosProximosFragment.newInstance(leagueId, season);
            } else {
                return PartidosPasadosFragment.newInstance(leagueId, season);
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}