package com.example.app2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app2.model.Noticia;
import com.example.app2.R;

import java.util.ArrayList;

public class AdapatadorNoticia extends ArrayAdapter<Noticia> {

    public AdapatadorNoticia(Context context, ArrayList<Noticia> noticias) {
        super(context, 0, noticias);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Noticia noticia = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.fila_notica, parent, false);
        }

        ImageView imagen = convertView.findViewById(R.id.imgNoticia);
        TextView titulo = convertView.findViewById(R.id.txtTituloNoticia);
        TextView fuente = convertView.findViewById(R.id.txtFuenteNoticia);
        TextView fecha = convertView.findViewById(R.id.txtFechaNoticia);

        titulo.setText(noticia.getTitle());
        fuente.setText(noticia.getSource());
        fecha.setText(noticia.getPublished_at());

        Glide.with(getContext())
                .load(noticia.getImage())
                .placeholder(R.drawable.baseline_newspaper_24)
                .error(R.drawable.baseline_settings_24)
                .into(imagen);

        return convertView;
    }
}