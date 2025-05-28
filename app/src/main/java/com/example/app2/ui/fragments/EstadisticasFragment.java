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
import com.example.app2.api.FootballDataService;
import com.example.app2.api.footballDataServiceInterfaces.MaximosGoleadoresCallBack;
import com.example.app2.model.GoleadorModel;
import com.example.app2.adapter.GoleadoresAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que muestra las estadísticas de máximos goleadores de una liga.
 *
 * Funcionalidades principales:
 * - Recibe el ID de la liga y la temporada como argumentos.
 * - Solicita a la API la lista de máximos goleadores usando FootballDataService.
 * - Muestra los goleadores en un RecyclerView usando GoleadoresAdapter.
 * - Muestra un ProgressBar mientras se cargan los datos.
 * - Muestra un mensaje de error si la carga falla.
 *
 * Uso típico:
 * - Se utiliza dentro de una actividad o ViewPager para mostrar las estadísticas de una liga seleccionada.
 * - Se instancia usando el método estático newInstance(int leagueId, int season).
 */
public class EstadisticasFragment extends Fragment {

    private static final String ARG_LEAGUE_ID = "league_id";
    private static final String ARG_SEASON = "season";

    private int leagueId;
    private int season;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private GoleadoresAdapter adapter;

    /**
     * Crea una nueva instancia del fragmento con los argumentos necesarios.
     * @param leagueId ID de la liga.
     * @param season Temporada.
     * @return Instancia de EstadisticasFragment.
     */
    public static EstadisticasFragment newInstance(int leagueId, int season) {
        EstadisticasFragment fragment = new EstadisticasFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LEAGUE_ID, leagueId);
        args.putInt(ARG_SEASON, season);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            leagueId = getArguments().getInt(ARG_LEAGUE_ID);
            season = getArguments().getInt(ARG_SEASON);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stast, container, false);
        recyclerView = view.findViewById(R.id.top_scorers_recycler);
        progressBar = view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        loadTopScorers();
    }

    /**
     * Configura el RecyclerView y su adaptador.
     */
    private void setupRecyclerView() {
        adapter = new GoleadoresAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Solicita la lista de máximos goleadores a la API y actualiza la interfaz según el resultado.
     */
    private void loadTopScorers() {
        progressBar.setVisibility(View.VISIBLE);

        FootballDataService footballDataService = new FootballDataService(getContext());
        footballDataService.obtenerMaximosGoleadores(leagueId, season, new MaximosGoleadoresCallBack() {
            @Override
            public void onSuccess(ArrayList<GoleadorModel> goleadorModelArrayList) {
                progressBar.setVisibility(View.GONE);
                adapter.updateData(goleadorModelArrayList);
            }

            @Override
            public void onError(String errorMessage) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}