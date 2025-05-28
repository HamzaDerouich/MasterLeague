package com.example.app2.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app2.model.Partido;
import com.example.app2.R;

import java.util.ArrayList;

public class AdaptadorPartido extends BaseAdapter {

    ArrayList<Partido> lista;
    Context context;

    // Constructor del adaptador que inicializa la lista de partidos
    public AdaptadorPartido(Context c) {
        context = c;
        lista = new ArrayList<>();

        Resources resources = c.getResources();
        String[] resultadosArray = resources.getStringArray(R.array.resultados_partidos);
        int[] imagenes = {R.drawable.manchestervsliverpoll, R.drawable.realmadridbarcelona, R.drawable.milavsjuventus, R.drawable.bayer, R.drawable.psg, R.drawable.galaxy, R.drawable.benfica};
        String[] descripcionesArray = resources.getStringArray(R.array.descripciones_partidos);
        String[] goleadoresArray = resources.getStringArray(R.array.goleadores_partidos);

        // Inicializar los objetos Partidos y agregarlos a la lista
        for (int i = 0; i < 7 ; i++) {
           lista.add(new Partido(resultadosArray[i],imagenes[i],descripcionesArray[i],goleadoresArray[i]));
        }
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View list = inflater.inflate(R.layout.filas_partido,viewGroup,false);
        TextView nombre = (TextView) list.findViewById(R.id.textViewTitle);
        ImageView imagen = (ImageView) list.findViewById(R.id.imageViewLogo);

        Partido partido = lista.get(i);

        nombre.setText(partido.getResultado());
        imagen.setImageResource(partido.getImagen());

        return list;
    }
}
