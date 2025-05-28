package com.example.app2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.app2.model.LigaModel;
import com.example.app2.R;

import java.util.ArrayList;

/**
 * Adapter personalizado para mostrar una lista de competiciones (ligas) en un ListView o Spinner.
 * Utiliza el modelo LigaModel para cada elemento de la lista.
 *
 * - Muestra el nombre de la liga, el país y el logo.
 * - Usa Glide para cargar el logo de la liga de forma eficiente.
 * - El layout de cada ítem es filas_lista.xml.
 */
public class CompeticionesAdapter extends ArrayAdapter<LigaModel> {

    /**
     * Constructor del adapter.
     * @param context Contexto de la aplicación.
     * @param ligas Lista de objetos LigaModel a mostrar.
     */
    public CompeticionesAdapter(Context context, ArrayList<LigaModel> ligas) {
        super(context, 0, ligas);
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

        LigaModel liga = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.filas_lista, parent, false);
        }

        // Referencias a los elementos de la vista
        ImageView imagen = convertView.findViewById(R.id.imgLiga);
        TextView nombre = convertView.findViewById(R.id.txtNombreLiga);
        TextView pais = convertView.findViewById(R.id.txtPaisLiga);

        // Asignar valores a los elementos
        nombre.setText(liga.getName());
        pais.setText(liga.getCountry().getName());

        // Cargar imagen del logo de la liga usando Glide
        Glide.with(getContext())
                .load(liga.getLogo())
                .placeholder(R.drawable.baseline_sports_soccer_24)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .into(imagen);

        return convertView;
    }
}