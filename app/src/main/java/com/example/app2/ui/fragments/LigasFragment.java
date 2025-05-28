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
import com.example.app2.api.FootballApiService;
import com.example.app2.model.Liga;
import com.example.app2.ui.InfoLigaActivity;

import java.util.ArrayList;

public class Ligas extends Fragment {

    private ListView listView;
    private ProgressBar progressBar;
    private CompeticionesAdapter adapter;
    private ArrayList<Liga> cacheLigas = new ArrayList<>();

    public Ligas() {
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
                Liga liga = cacheLigas.get(position);
                Intent intent = new Intent(getContext(), InfoLigaActivity.class);
                intent.putExtra("liga", liga);
                startActivity(intent);
            }
        });

        return rootView;
    }



    private void cargarLigas() {

        progressBar.setVisibility(View.VISIBLE);
        cacheLigas.clear();

        FootballApiService api = new FootballApiService(getContext());
        api.obtenerLigas(new FootballApiService.LigasCallback() {
            @Override
            public void onSuccess(ArrayList<Liga> ligas) {
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