package com.example.app2.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app2.ui.fragments.ClasificacionFragment;
import com.example.app2.ui.fragments.EstadisticasFragment;
import com.example.app2.ui.fragments.PartidosFragment;

public class DetalleLigaPagerAdapter extends FragmentStateAdapter {

    private final int leagueId;
    private final int season;

    public DetalleLigaPagerAdapter(@NonNull FragmentActivity fragmentActivity, int leagueId, int season) {
        super(fragmentActivity);
        this.leagueId = leagueId;
        this.season = season;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return ClasificacionFragment.newInstance(leagueId, season);
            case 1:
                return PartidosFragment.newInstance(leagueId, season);
            case 2:
                return EstadisticasFragment.newInstance(leagueId, season);
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}