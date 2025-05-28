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

import com.example.app2.R;
import com.example.app2.adapter.GoleadoresAdapter;
import com.example.app2.adapter.LigasGoleadoresAdapter;
import com.example.app2.api.FootballDataService;
import com.example.app2.api.footballDataServiceInterfaces.MaximosGoleadoresCallBack;
import com.example.app2.model.GoleadorModel;
import com.example.app2.model.LigaModel;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

public class SecctionGoleadoresFragment extends Fragment {

    private int leagueId = 61; // ID por defecto (LaLiga)
    private int season = 2023; // Temporada actual

    private RecyclerView rvLigas;
    private RecyclerView rvGoleadores;
    private ProgressBar progressBar;
    private LigasGoleadoresAdapter ligasAdapter;
    private GoleadoresAdapter goleadoresAdapter;

    public SecctionGoleadoresFragment() {
        // Required empty public constructor
    }

    public static SecctionGoleadoresFragment newInstance() {
        return new SecctionGoleadoresFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Cambiamos el layout por uno adecuado
        return inflater.inflate(R.layout.fragment_goleadores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvLigas = view.findViewById(R.id.rv_ligas_goleadores);
        rvGoleadores = view.findViewById(R.id.rv_goleadores);
        progressBar = view.findViewById(R.id.progress_bar);

        setupLigasRecyclerView();
        setupGoleadoresRecyclerView();
        cargarLigas();
    }

    private void setupLigasRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        rvLigas.setLayoutManager(layoutManager);

        // Efecto snap para el carrusel
        new GravitySnapHelper(GravitySnapHelper.GRAVITY_START).attachToRecyclerView(rvLigas);

        ligasAdapter = new LigasGoleadoresAdapter(requireContext(), (liga, position) -> {
            leagueId = liga.getId();
            cargarGoleadores();
            ligasAdapter.setSelectedPosition(position);
        });
        rvLigas.setAdapter(ligasAdapter);
    }

    private void setupGoleadoresRecyclerView() {
        rvGoleadores.setLayoutManager(new LinearLayoutManager(requireContext()));
        goleadoresAdapter = new GoleadoresAdapter(new ArrayList<>());
        rvGoleadores.setAdapter(goleadoresAdapter);
    }

    private void cargarLigas() {
        progressBar.setVisibility(View.VISIBLE);
        FootballDataService service = new FootballDataService(requireContext());
        service.obtenerLigas(ligas -> {
            requireActivity().runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                if (ligas != null && !ligas.isEmpty()) {
                    ligasAdapter.setLigas(ligas);
                    // Cargar goleadores de la primera liga por defecto
                    leagueId = ligas.get(0).getId();
                    cargarGoleadores();
                }
            });
        }, error -> {
            requireActivity().runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Error al cargar ligas: " + error, Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void cargarGoleadores() {
        progressBar.setVisibility(View.VISIBLE);
        FootballDataService service = new FootballDataService(requireContext());
        service.obtenerMaximosGoleadores(leagueId, season, new MaximosGoleadoresCallBack() {
            @Override
            public void onSuccess(ArrayList<GoleadorModel> goleadores) {
                requireActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    goleadoresAdapter.updateData(goleadores);
                });
            }

            @Override
            public void onError(String error) {
                requireActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Error al cargar goleadores: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}