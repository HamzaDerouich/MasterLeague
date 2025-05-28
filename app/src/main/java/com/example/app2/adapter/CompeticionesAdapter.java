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
import com.example.app2.model.Liga;
import com.example.app2.R;

import java.util.ArrayList;

public class AdapatadorCompeticiones extends ArrayAdapter<Liga> {

    public AdapatadorCompeticiones(Context context, ArrayList<Liga> ligas) {
        super(context, 0, ligas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Liga liga = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.filas_lista, parent, false);
        }

        // Usando los IDs correctos de filas_lista.xml

        ImageView imagen = convertView.findViewById(R.id.imgLiga);
        TextView nombre = convertView.findViewById(R.id.txtNombreLiga);
        TextView pais = convertView.findViewById(R.id.txtPaisLiga);

        nombre.setText(liga.getNombre());
        pais.setText(liga.getPais());

        // Cargar imagen con Glide
        Glide.with(getContext())
                .load(liga.getImagenUrl())
                .placeholder(R.drawable.baseline_sports_soccer_24)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .into(imagen);

        return convertView;
    }
}