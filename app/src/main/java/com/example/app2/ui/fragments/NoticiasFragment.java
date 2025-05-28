package com.example.app2.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.app2.R;
import com.example.app2.adapter.NoticiaAdapter;
import com.example.app2.api.NewsDataService;
import com.example.app2.model.NoticiaModel;

import java.util.ArrayList;

/**
 * Fragmento que muestra la lista de noticias deportivas en la aplicación.
 *
 * Funcionalidades principales:
 * - Solicita la lista de noticias deportivas a la API usando NewsDataService.
 * - Muestra las noticias en un ListView usando NoticiaAdapter.
 * - Permite al usuario pulsar sobre una noticia para ver su detalle en DetalleNoticiaFragment.
 * - Gestiona errores mostrando mensajes mediante Toast.
 *
 * Uso típico:
 * - Se utiliza en la sección de noticias de la app para mostrar las noticias deportivas más recientes.
 * - Al pulsar una noticia, se navega al detalle de esa noticia dentro de la misma actividad.
 */
public class NoticiasFragment extends Fragment {

    private ListView listView;
    private ArrayList<NoticiaModel> listadoNoticias = new ArrayList<>();
    private NoticiaAdapter adapatadorNoticia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_noticias, container, false);

        listView = rootView.findViewById(R.id.listViwLigas);
        adapatadorNoticia = new NoticiaAdapter(getContext(), listadoNoticias);
        listView.setAdapter(adapatadorNoticia);

        obtenerNoticiasDeportivas();

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            NoticiaModel noticia = listadoNoticias.get(position);
            DetalleNoticiaFragment detalleFragment = DetalleNoticiaFragment.newInstance(noticia);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, detalleFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

    /**
     * Solicita la lista de noticias deportivas a la API y actualiza la interfaz según el resultado.
     */
    private void obtenerNoticiasDeportivas() {
        NewsDataService newsApi = new NewsDataService(getContext());
        newsApi.obtenerNoticias(new NewsDataService.NewsCallback() {
            @Override
            public void onSuccess(ArrayList<NoticiaModel> noticias) {
                listadoNoticias.clear();
                listadoNoticias.addAll(noticias);
                adapatadorNoticia.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}