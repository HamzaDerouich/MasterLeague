package com.example.app2.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2.R;
import com.example.app2.adapter.LigasGoleadoresAdapter;
import com.example.app2.data.FavoritosRepository;
import com.example.app2.model.FavoritoLigaModel;
import com.example.app2.model.LigaModel;
import com.example.app2.ui.activities.DetallesLigaActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que muestra la lista de ligas favoritas del usuario.
 *
 * Funcionalidades principales:
 * - Obtiene las ligas favoritas almacenadas en la base de datos local usando FavoritosRepository.
 * - Convierte los favoritos a objetos LigaModel para su visualización.
 * - Muestra las ligas favoritas en un RecyclerView horizontal usando LigasGoleadoresAdapter.
 * - Permite al usuario pulsar sobre una liga para ver sus detalles en DetallesLigaActivity.
 * - Muestra mensajes de depuración en el log y permite mostrar un mensaje si no hay favoritos.
 *
 * Uso típico:
 * - Se utiliza en la sección de favoritos de la app para mostrar rápidamente las ligas marcadas como favoritas por el usuario.
 */
public class FavoritosFragment extends Fragment {

    private RecyclerView rvFavoritos;
    private LigasGoleadoresAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favoritos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFavoritos = view.findViewById(R.id.rv_favoritos);
        rvFavoritos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        FavoritosRepository repo = new FavoritosRepository(getContext());
        List<FavoritoLigaModel> favoritos = repo.obtenerFavoritos();

        // Log para depuración
        Log.d("FavoritosFragment", "Favoritos encontrados: " + favoritos.size());

        List<LigaModel> ligas = new ArrayList<>();
        for (FavoritoLigaModel fav : favoritos) {
            LigaModel liga = new LigaModel();
            liga.setId(fav.getLigaId());
            liga.setName(fav.getNombre());
            liga.setLogo(fav.getLogoUrl());
            ligas.add(liga);
        }

        // Log para depuración
        Log.d("FavoritosFragment", "Ligas convertidas: " + ligas.size());

        adapter = new LigasGoleadoresAdapter(getContext(), (liga, pos) -> {
            // Abrir detalles de liga al pulsar
            Intent intent = new Intent(getContext(), DetallesLigaActivity.class);
            intent.putExtra("liga", liga); // LigaModel debe ser Parcelable
            startActivity(intent);
        });
        adapter.setLigas(ligas);
        rvFavoritos.setAdapter(adapter);

        // Mostrar mensaje si no hay favoritos
        if (ligas.isEmpty()) {
            // Puedes mostrar un mensaje en pantalla si lo deseas
            Log.d("FavoritosFragment", "No hay ligas favoritas para mostrar.");
        }
    }
}