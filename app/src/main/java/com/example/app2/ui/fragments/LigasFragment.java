package com.example.app2.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.app2.R;
import com.example.app2.adapter.CompeticionesAdapter;
import com.example.app2.api.FootballDataService;
import com.example.app2.api.footballDataServiceInterfaces.LigasCallback;
import com.example.app2.model.LigaModel;
import com.example.app2.ui.activities.DetallesLigaActivity;
import com.example.app2.ui.activities.InfoLigaActivity;

import java.util.ArrayList;

/**
 * Fragmento que muestra la lista de ligas disponibles en la aplicación.
 *
 * Funcionalidades principales:
 * - Solicita la lista de ligas a la API usando FootballDataService.
 * - Muestra las ligas en un ListView usando CompeticionesAdapter.
 * - Muestra un ProgressBar mientras se cargan los datos.
 * - Permite al usuario pulsar sobre una liga para ver sus detalles en DetallesLigaActivity.
 * - Gestiona errores mostrando mensajes mediante Toast.
 * - Utiliza un caché local (cacheLigas) para almacenar temporalmente las ligas cargadas.
 *
 * Uso típico:
 * - Se utiliza en la sección de ligas de la app para mostrar todas las competiciones disponibles.
 * - Al pulsar una liga, se navega a la pantalla de detalles de esa liga.
 */
public class LigasFragment extends Fragment {

    private ListView listView;
    private ProgressBar progressBar;
    private CompeticionesAdapter adapter;
    private ArrayList<LigaModel> cacheLigas = new ArrayList<>();

    public LigasFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ligas, container, false);

        listView = rootView.findViewById(R.id.listViwLigas);
        progressBar = rootView.findViewById(R.id.progressBar);
        adapter = new CompeticionesAdapter(getContext(), cacheLigas);
        listView.setAdapter(adapter);

        cargarLigas();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                LigaModel liga = cacheLigas.get(position);
                Intent intent = new Intent(getContext(), DetallesLigaActivity.class);
                intent.putExtra("liga", liga);
                startActivity(intent);
            }
        });

        return rootView;
    }

    /**
     * Solicita la lista de ligas a la API y actualiza la interfaz según el resultado.
     */
    private void cargarLigas() {
        progressBar.setVisibility(View.VISIBLE);
        cacheLigas.clear();

        FootballDataService api = new FootballDataService(getContext());
        api.obtenerLigas(new LigasCallback() {
            @Override
            public void onSuccess(ArrayList<LigaModel> ligas) {
                progressBar.setVisibility(View.GONE);
                cacheLigas.addAll(ligas);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                // Puedes cargar datos locales de respaldo aquí si lo deseas
            }
        });
    }
}