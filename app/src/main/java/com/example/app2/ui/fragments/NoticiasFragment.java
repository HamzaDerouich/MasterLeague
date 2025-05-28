package com.example.app2.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.app2.R;
import com.example.app2.adapter.NoticiaAdapter;
import com.example.app2.api.NewsDataService;
import com.example.app2.model.NoticiaModel;

import java.util.ArrayList;

public class NoticiasInfoFragment extends Fragment {

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
            NoticiaDetalleFragment detalleFragment = NoticiaDetalleFragment.newInstance(noticia);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, detalleFragment)
                    .addToBackStack(null)
                    .commit();

        });

        return rootView;
    }

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