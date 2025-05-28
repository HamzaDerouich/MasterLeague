package com.example.app2.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.app2.R;
import com.example.app2.adapter.PartidosAdapter;
import com.example.app2.model.Partido;
import com.example.app2.ui.PartidoInfoActivity;

import java.util.ArrayList;


public class Partidos extends Fragment {

    ListView listView;
    static ArrayList<Partido> listado_partidos = new ArrayList<Partido>() ;

    public Partidos() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ligas, container, false);

        String[] resultadosArray = getResources().getStringArray(R.array.resultados_partidos);
        int[] imagenes = {R.drawable.manchestervsliverpoll, R.drawable.realmadridbarcelona, R.drawable.milavsjuventus, R.drawable.bayer, R.drawable.psg, R.drawable.galaxy, R.drawable.benfica};
        String[] descripcionesArray = getResources().getStringArray(R.array.descripciones_partidos);
        String[] goleadoresArray = getResources().getStringArray(R.array.goleadores_partidos);


        listView = rootView.findViewById(R.id.listViwLigas);
        PartidosAdapter adaptadorPartido = new PartidosAdapter(getContext());
        listView.setAdapter(adaptadorPartido);

        for (int i = 0; i < 7; i++)
        {
            listado_partidos.add(new Partido(resultadosArray[i], imagenes[i], descripcionesArray[i], goleadoresArray[i]));

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Partido partido = listado_partidos.get(i);
                        Intent intent = new Intent(getContext(), PartidoInfoActivity.class);
                        intent.putExtra("partido", partido);
                        startActivity(intent);
                    }
                });
            }
        });

        return rootView;
    }
}