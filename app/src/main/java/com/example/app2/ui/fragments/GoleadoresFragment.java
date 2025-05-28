package com.example.app2.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.app2.R;
import com.example.app2.adapter.GoleadoresAdapter;
import com.example.app2.api.FootballDataService;
import com.example.app2.api.footballDataServiceInterfaces.MaximosGoleadoresCallBack;
import com.example.app2.model.GoleadorModel;

import java.util.ArrayList;

/**
 * Fragmento que muestra la lista de máximos goleadores de una liga.
 *
 * Funcionalidades principales:
 * - Recibe el ID de la liga y la temporada como argumentos.
 * - Solicita a la API la lista de máximos goleadores usando FootballDataService.
 * - Muestra los goleadores en un RecyclerView usando GoleadoresAdapter.
 * - Permite recargar la lista mediante SwipeRefreshLayout (deslizar para refrescar).
 * - Muestra un ProgressBar mientras se cargan los datos.
 * - Muestra un mensaje si no hay datos o si ocurre un error.
 *
 * Uso típico:
 * - Se utiliza dentro de una actividad o ViewPager para mostrar los goleadores de una liga seleccionada.
 * - Se instancia usando el método estático newInstance(int leagueId, int season).
 */
public class GoleadoresFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView txtEmpty;
    private SwipeRefreshLayout swipeRefresh;
    private GoleadoresAdapter adapter;
    private int leagueId;
    private int season;

    /**
     * Crea una nueva instancia del fragmento con los argumentos necesarios.
     * @param leagueId ID de la liga.
     * @param season Temporada.
     * @return Instancia de GoleadoresFragment.
     */
    public static GoleadoresFragment newInstance(int leagueId, int season) {
        GoleadoresFragment fragment = new GoleadoresFragment();
        Bundle args = new Bundle();
        args.putInt("leagueId", leagueId);
        args.putInt("season", season);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            leagueId = getArguments().getInt("leagueId");
            season = getArguments().getInt("season");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goleadores, container, false);

        // Inicializar vistas
        recyclerView = view.findViewById(R.id.recycler_goleadores);
        progressBar = view.findViewById(R.id.progress_bar);
        txtEmpty = view.findViewById(R.id.txt_empty);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new GoleadoresAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Configurar SwipeRefresh
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark);

        // Cargar datos
        loadData();

        return view;
    }

    /**
     * Carga la lista de goleadores desde la API y actualiza la interfaz.
     */
    private void loadData() {
        showLoading(true);

        FootballDataService service = new FootballDataService(getContext());
        service.obtenerMaximosGoleadores(leagueId, season, new MaximosGoleadoresCallBack() {
            @Override
            public void onSuccess(ArrayList<GoleadorModel> goleadores) {
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    showLoading(false);
                    if (goleadores == null || goleadores.isEmpty()) {
                        showEmptyState(true);
                    } else {
                        showEmptyState(false);
                        adapter.updateData(goleadores);
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    showLoading(false);
                    showEmptyState(true);
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    /**
     * Muestra u oculta el indicador de carga.
     */
    private void showLoading(boolean isLoading) {
        if (swipeRefresh.isRefreshing() && !isLoading) {
            swipeRefresh.setRefreshing(false);
        }
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    /**
     * Muestra u oculta el mensaje de estado vacío.
     */
    private void showEmptyState(boolean show) {
        txtEmpty.setVisibility(show ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    /**
     * Recarga la lista de goleadores al hacer swipe-to-refresh.
     */
    @Override
    public void onRefresh() {
        loadData();
    }
}