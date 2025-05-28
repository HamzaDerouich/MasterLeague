package com.example.app2.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.app2.R;
import com.example.app2.model.NoticiaModel;

public class NoticiaDetalleFragment extends Fragment {

    private static final String ARG_NOTICIA = "noticia";

    public static NoticiaDetalleFragment newInstance(NoticiaModel noticia) {
        NoticiaDetalleFragment fragment = new NoticiaDetalleFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTICIA, (Parcelable) noticia);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noticia_detalle, container, false);

        if (getArguments() != null) {
            NoticiaModel noticia = getArguments().getParcelable(ARG_NOTICIA);
            if (noticia != null) {
                mostrarDetalleNoticia(view, noticia);
            }
        }

        return view;
    }

    private void mostrarDetalleNoticia(View view, NoticiaModel noticia) {
        TextView titulo = view.findViewById(R.id.tv_titulo);
        TextView descripcion = view.findViewById(R.id.tv_descripcion);
        TextView fecha = view.findViewById(R.id.tv_fecha);
        TextView autor = view.findViewById(R.id.tv_autor);
        ImageView imagen = view.findViewById(R.id.iv_imagen);

        titulo.setText(noticia.getTitle());
        descripcion.setText(noticia.getDescription());
        fecha.setText(noticia.getPublished_at());
        autor.setText(noticia.getAuthor() != null ? noticia.getAuthor() : "Autor desconocido");

        Glide.with(this)
                .load(noticia.getUrl())
                .placeholder(R.drawable.ic_empty_state)
                .error(R.drawable.ic_empty_state)
                .into(imagen);
    }
}