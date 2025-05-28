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
import com.example.app2.api.footballDataServiceInterfaces.LigasCallback;
import com.example.app2.model.GoleadorModel;
import com.example.app2.model.LigaModel;

import java.util.ArrayList;

/**
 * Fragmento que muestra la sección de máximos goleadores por liga.
 *
 * Funcionalidades principales:
 * - Solicita la lista de ligas a la API usando FootballDataService y las muestra en un carrusel horizontal (RecyclerView).
 * - Permite al usuario seleccionar una liga del carrusel para ver sus máximos goleadores.
 * - Solicita a la API la lista de máximos goleadores de la liga seleccionada y los muestra en un RecyclerView vertical.
 * - Muestra un ProgressBar mientras se cargan los datos.
 * - Gestiona errores mostrando mensajes mediante Toast.
 *
 * Uso típico:
 * - Se utiliza en la sección de estadísticas o goleadores de la app para consultar los máximos goleadores de cada liga.
 * - El usuario puede cambiar de liga usando el carrusel y ver los goleadores actualizados.
 */
public class SecctionGoleadoresFragment extends Fragment {

    private int leagueId = 61; // ID por defecto (LaLiga)
    private int season = 2023; // Temporada actual

    private RecyclerView rvLigas;
    private RecyclerView rvGoleadores;
    private ProgressBar progressBar;
    private LigasGoleadoresAdapter ligasAdapter;
    private GoleadoresAdapter goleadoresAdapter;

    public SecctionGoleadoresFragment() {
        // Constructor vacío requerido
    }

    /**
     * Crea una nueva instancia del fragmento.
     */
    public static SecctionGoleadoresFragment newInstance() {
        return new SecctionGoleadoresFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el layout del fragmento
        return inflater.inflate(R.layout.fragment_secction_goleadores, container, false);
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

    /**
     * Configura el RecyclerView horizontal para mostrar las ligas.
     */
    private void setupLigasRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        rvLigas.setLayoutManager(layoutManager);

        ligasAdapter = new LigasGoleadoresAdapter(requireContext(), (liga, position) -> {
            leagueId = liga.getId();
            cargarGoleadores();
            ligasAdapter.setSelectedPosition(position);
        });
        rvLigas.setAdapter(ligasAdapter);
    }

    /**
     * Configura el RecyclerView vertical para mostrar los goleadores.
     */
    private void setupGoleadoresRecyclerView() {
        rvGoleadores.setLayoutManager(new LinearLayoutManager(requireContext()));
        goleadoresAdapter = new GoleadoresAdapter(new ArrayList<>());
        rvGoleadores.setAdapter(goleadoresAdapter);
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
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    if (ligas != null && !ligas.isEmpty()) {
                        ligasAdapter.setLigas(ligas);
                        // Cargar goleadores de la primera liga por defecto
                        leagueId = ligas.get(0).getId();
                        cargarGoleadores();
                    }
                });
            }

            @Override
            public void onError(String error) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Error al cargar ligas: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    /**
     * Solicita la lista de máximos goleadores de la liga seleccionada a la API y actualiza la interfaz.
     */
    private void cargarGoleadores() {
        progressBar.setVisibility(View.VISIBLE);
        FootballDataService service = new FootballDataService(requireContext());
        service.obtenerMaximosGoleadores(leagueId, season, new MaximosGoleadoresCallBack() {
            @Override
            public void onSuccess(ArrayList<GoleadorModel> goleadores) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    goleadoresAdapter.updateData(goleadores);
                });
            }

            @Override
            public void onError(String error) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Error al cargar goleadores: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}