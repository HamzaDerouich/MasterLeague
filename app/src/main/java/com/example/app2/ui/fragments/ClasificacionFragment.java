package com.example.app2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClasificacionFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clasificacion, container, false);

        recyclerView = rootView.findViewById(R.id.standings_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Aquí agregarías tu adapter, por ejemplo:
        // recyclerView.setAdapter(new StandingsAdapter(yourData));

        return rootView;
    }
}
