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
import com.example.app2.api.footballDataServiceInterfaces.ClasificacionCallBack;
import com.example.app2.model.ClasificacionModel;
import com.example.app2.adapter.ClasificacionAdapter;

import java.util.ArrayList;

/**
 * Fragmento que muestra la clasificación (tabla de posiciones) de una liga de fútbol.
 *
 * Funcionalidades principales:
 * - Recibe el ID de la liga y la temporada como argumentos.
 * - Solicita la clasificación a la API usando FootballDataService.
 * - Muestra la tabla de posiciones en un RecyclerView usando ClasificacionAdapter.
 * - Muestra un ProgressBar mientras se cargan los datos.
 * - Muestra un mensaje de error si la carga falla.
 *
 * Uso típico:
 * - Se utiliza dentro de una actividad o ViewPager para mostrar la clasificación de una liga seleccionada.
 * - Se instancia usando el método estático newInstance(int leagueId, int season).
 */
public class ClasificacionFragment extends Fragment {

    private static final String ARG_LEAGUE_ID = "league_id";
    private static final String ARG_SEASON = "season";

    private int leagueId;
    private int season;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ClasificacionAdapter adapter;

    /**
     * Crea una nueva instancia del fragmento con los argumentos necesarios.
     * @param leagueId ID de la liga.
     * @param season Temporada.
     * @return Instancia de ClasificacionFragment.
     */
    public static ClasificacionFragment newInstance(int leagueId, int season) {
        ClasificacionFragment fragment = new ClasificacionFragment();
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
        View view = inflater.inflate(R.layout.fragment_clasificacion, container, false);
        recyclerView = view.findViewById(R.id.standings_recycler);
        progressBar = view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        cargarClasificacion();
    }

    /**
     * Configura el RecyclerView y su adaptador.
     */
    private void setupRecyclerView() {
        adapter = new ClasificacionAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Solicita la clasificación a la API y actualiza la interfaz según el resultado.
     */
    private void cargarClasificacion() {
        progressBar.setVisibility(View.VISIBLE);
        FootballDataService service = new FootballDataService(requireContext());
        service.obtenerClasificacion(leagueId, season, new ClasificacionCallBack() {
            @Override
            public void onSuccess(ArrayList<ClasificacionModel> clasificacionModelArrayList) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    adapter.updateData(clasificacionModelArrayList);
                    progressBar.setVisibility(View.GONE);
                });
            }

            @Override
            public void onError(String errorMessage) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Error al cargar la clasificación", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                });
            }
        });
    }
}