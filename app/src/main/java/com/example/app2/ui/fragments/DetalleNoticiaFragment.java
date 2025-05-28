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

/**
 * Fragmento que muestra el detalle de una noticia seleccionada.
 *
 * Funcionalidades principales:
 * - Recibe un objeto NoticiaModel por argumentos y muestra sus detalles (título, descripción, fecha, autor e imagen).
 * - Utiliza Glide para cargar la imagen de la noticia.
 * - Permite volver atrás mediante un botón de retroceso.
 *
 * Uso típico:
 * - Se utiliza en la sección de noticias de la app para mostrar la información completa de una noticia seleccionada desde una lista.
 * - Se instancia usando el método estático newInstance(NoticiaModel noticia).
 */
public class DetalleNoticiaFragment extends Fragment {

    private static final String ARG_NOTICIA = "noticia";
    private ImageView btnBack;

    /**
     * Crea una nueva instancia del fragmento con la noticia como argumento.
     * @param noticia Objeto NoticiaModel a mostrar.
     * @return Instancia de DetalleNoticiaFragment.
     */
    public static DetalleNoticiaFragment newInstance(NoticiaModel noticia) {
        DetalleNoticiaFragment fragment = new DetalleNoticiaFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTICIA, (Parcelable) noticia);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noticia_detalle, container, false);

        // Recupera la noticia de los argumentos y muestra sus detalles
        if (getArguments() != null) {
            NoticiaModel noticia = getArguments().getParcelable(ARG_NOTICIA);
            if (noticia != null) {
                mostrarDetalleNoticia(view, noticia);
            }
        }

        // Botón para volver atrás
        btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    /**
     * Muestra los detalles de la noticia en la interfaz.
     * @param view Vista raíz del fragmento.
     * @param noticia Noticia a mostrar.
     */
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
                .load(noticia.getImage())
                .placeholder(R.drawable.ic_empty_state)
                .error(R.drawable.ic_empty_state)
                .into(imagen);
    }
}