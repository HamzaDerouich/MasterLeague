package com.example.app2.ui.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.app2.ui.GoleadoresInfoActivity;
import com.example.app2.R;
import com.example.app2.adapter.GoleadoresAdapter;
import com.example.app2.model.Goleador;

import java.util.ArrayList;


public class Goleadores extends Fragment {

    ListView listView;
    static ArrayList<Goleador> listado_goleadores = new ArrayList<Goleador>() ;

    public Goleadores() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ligas, container, false);

        Resources resources = rootView.getResources();
        String[] nombres = resources.getStringArray(R.array.goleadores_futbol);
        int[] imagenes_array = {R.drawable.messi, R.drawable.cristiano, R.drawable.mbape, R.drawable.haland, R.drawable.lewandoski, R.drawable.neymar};
        String[] descripcon_array = resources.getStringArray(R.array.descripciones_goleadores_futbol);
        String[] goles_array = resources.getStringArray(R.array.total_goles_futbol);

        listView = rootView.findViewById(R.id.listViwLigas);
        GoleadoresAdapter adapatadorGoleadores = new GoleadoresAdapter(getContext());
        listView.setAdapter(adapatadorGoleadores);

        listado_goleadores.clear();
        for (int i = 0; i < 6; i++) {
            listado_goleadores.add(new Goleador(nombres[i], imagenes_array[i], descripcon_array[i], goles_array[i]));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Goleador resultado = listado_goleadores.get(i);

                Intent intent = new Intent(getContext(), GoleadoresInfoActivity.class);
                intent.putExtra("goleador", resultado);
                startActivity(intent);
            }
        });

        return rootView;
    }
}