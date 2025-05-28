package com.example.app2.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app2.model.Goleador;
import com.example.app2.R;

import java.util.ArrayList;

public class AdapatadorGoleadores extends BaseAdapter {

    ArrayList<Goleador> lista;
    Context context;

    AdapatadorGoleadores(Context c )
    {
        context = c;
        lista = new ArrayList<Goleador>();

        Resources resources = c.getResources();
        String[] nombres = resources.getStringArray(R.array.goleadores_futbol);
        int[] imagenes_array = {R.drawable.messi,  R.drawable.cristiano, R.drawable.mbape , R.drawable.haland, R.drawable.lewandoski , R.drawable.neymar};
        String[] descripcon_array = resources.getStringArray(R.array.descripciones_goleadores_futbol);
        String[] goles_array = resources.getStringArray(R.array.total_goles_futbol);

        for (int i = 0; i < 6; i++) {
            lista.add(new Goleador(nombres[i], imagenes_array[i], descripcon_array[i],goles_array[i]));
        }


    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View list = inflater.inflate(R.layout.fila_notica,viewGroup,false);

        TextView nombre = (TextView) list.findViewById(R.id.textViewTitle);
        ImageView imagen = (ImageView) list.findViewById(R.id.imageViewLogo);

        Goleador goleador = lista.get(i);

        nombre.setText(goleador.getNombre());
        imagen.setImageResource(goleador.getImagen());

        return list;
    }
}
