package com.example.app2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app2.model.NoticiaModel;
import com.example.app2.R;

import java.util.ArrayList;

/**
 * Adapter personalizado para mostrar una lista de noticias en un ListView.
 * Utiliza el modelo NoticiaModel para cada elemento de la lista.
 *
 * - Muestra el título, la fuente, la fecha y la imagen de cada noticia.
 * - Usa Glide para cargar la imagen de la noticia de forma eficiente.
 * - El layout de cada ítem es fila_notica.xml.
 */
public class NoticiaAdapter extends ArrayAdapter<NoticiaModel> {

    /**
     * Constructor del adapter.
     * @param context Contexto de la aplicación.
     * @param noticias Lista de objetos NoticiaModel a mostrar.
     */
    public NoticiaAdapter(Context context, ArrayList<NoticiaModel> noticias) {
        super(context, 0, noticias);
    }

    /**
     * Método que genera la vista para cada elemento de la lista.
     * @param position Posición del elemento en la lista.
     * @param convertView Vista reciclada.
     * @param parent ViewGroup padre.
     * @return Vista configurada para el elemento.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoticiaModel noticia = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.fila_notica, parent, false);
        }

        // Referencias a los elementos de la vista
        ImageView imagen = convertView.findViewById(R.id.imgNoticia);
        TextView titulo = convertView.findViewById(R.id.txtTituloNoticia);
        TextView fuente = convertView.findViewById(R.id.txtFuenteNoticia);
        TextView fecha = convertView.findViewById(R.id.txtFechaNoticia);

        // Asignar valores a los elementos
        titulo.setText(noticia.getTitle());
        fuente.setText(noticia.getSource());
        fecha.setText(noticia.getPublished_at());

        // Cargar imagen de la noticia usando Glide
        Glide.with(getContext())
                .load(noticia.getImage())
                .placeholder(R.drawable.baseline_newspaper_24)
                .error(R.drawable.baseline_settings_24)
                .into(imagen);

        return convertView;
    }
}