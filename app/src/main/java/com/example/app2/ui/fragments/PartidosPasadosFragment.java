package com.example.app2.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2.R;
import com.example.app2.adapter.PartidosAdapter;
import com.example.app2.api.FootballDataService;
import com.example.app2.api.footballDataServiceInterfaces.ResultadosPasadosCallBack;
import com.example.app2.model.PartidoResultadoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que muestra la lista de partidos pasados de una liga.
 *
 * Funcionalidades principales:
 * - Recibe el ID de la liga y la temporada como argumentos.
 * - Solicita a la API la lista de partidos pasados usando FootballDataService.
 * - Muestra los partidos en un RecyclerView usando PartidosAdapter.
 * - Permite cargar más partidos con un botón "Cargar más" (paginación simple).
 * - Muestra un ProgressBar mientras se cargan los datos.
 * - Muestra una imagen si no hay datos disponibles.
 * - Gestiona errores mostrando mensajes y estados visuales.
 *
 * Uso típico:
 * - Se utiliza como parte de un ViewPager o pestaña para mostrar los partidos ya jugados de una liga seleccionada.
 * - Se instancia usando el método estático newInstance(int leagueId, int season).
 */
public class PartidosPasadosFragment extends Fragment {

    private static final String ARG_LEAGUE_ID = "league_id";
    private static final String ARG_SEASON = "season";
    private static final int DEFAULT_MATCHES_LIMIT = 10;
    private static final String TAG = "PartidosPasadosFragment";

    private int currentLimit = DEFAULT_MATCHES_LIMIT;
    private int leagueId;
    private int season;

    private ImageView imageViewDataNoAviable;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Button loadMoreButton;
    private PartidosAdapter adapter;

    /**
     * Crea una nueva instancia del fragmento con los argumentos necesarios.
     * @param leagueId ID de la liga.
     * @param season Temporada.
     * @return Instancia de PartidosPasadosFragment.
     */
    public static PartidosPasadosFragment newInstance(int leagueId, int season) {
        PartidosPasadosFragment fragment = new PartidosPasadosFragment();
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
        View view = inflater.inflate(R.layout.fragment_partidos_pasados, container, false);
        recyclerView = view.findViewById(R.id.matches_recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        imageViewDataNoAviable = view.findViewById(R.id.imageViewDataNoAviable);
        loadMoreButton = view.findViewById(R.id.load_more_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PartidosAdapter(new ArrayList<>(), true);
        recyclerView.setAdapter(adapter);

        loadMoreButton.setOnClickListener(v -> {
            currentLimit += DEFAULT_MATCHES_LIMIT;
            loadPastMatches();
        });

        loadPastMatches();

        return view;
    }

    /**
     * Carga los partidos pasados desde la API y actualiza la interfaz.
     * Implementa paginación simple aumentando el límite de partidos mostrados.
     */
    private void loadPastMatches() {
        progressBar.setVisibility(View.VISIBLE);
        FootballDataService footballDataService = new FootballDataService(requireContext());
        footballDataService.obtenerResultadosPasados(leagueId, season, new ResultadosPasadosCallBack() {
            @Override
            public void onSuccess(List<PartidoResultadoModel> partidoResultadoModelList) {
                Log.d(TAG, "Partidos pasados obtenidos: " + partidoResultadoModelList.size());
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);

                    if (adapter != null && partidoResultadoModelList != null && !partidoResultadoModelList.isEmpty()) {
                        List<PartidoResultadoModel> limitedList = partidoResultadoModelList.subList(
                                0, Math.min(currentLimit, partidoResultadoModelList.size()));
                        adapter.updateData(limitedList);
                        loadMoreButton.setVisibility(partidoResultadoModelList.size() > currentLimit ? View.VISIBLE : View.GONE);
                        imageViewDataNoAviable.setVisibility(View.GONE);
                    } else {
                        adapter.updateData(new ArrayList<>());
                        imageViewDataNoAviable.setVisibility(View.VISIBLE);
                        loadMoreButton.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Error al obtener partidos pasados: " + errorMessage);
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    adapter.updateData(new ArrayList<>());
                    imageViewDataNoAviable.setVisibility(View.VISIBLE);
                    loadMoreButton.setVisibility(View.GONE);
                });
            }
        });
    }
}