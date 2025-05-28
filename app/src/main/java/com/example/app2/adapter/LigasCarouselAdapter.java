package com.example.app2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app2.R;
import com.example.app2.model.LigaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter para mostrar un carrusel de ligas en un RecyclerView horizontal.
 * Utiliza el modelo LigaModel para cada ítem.
 *
 * - Muestra el logo y el nombre de cada liga.
 * - Usa Glide para cargar el logo de la liga.
 * - Permite manejar el evento de click sobre una liga mediante la interfaz OnLigaClickListener.
 * - El layout de cada ítem es item_liga_carousel.xml.
 */
public class LigasCarouselAdapter extends RecyclerView.Adapter<LigasCarouselAdapter.LigaViewHolder> {

    private Context context;
    private List<LigaModel> ligas;
    private OnLigaClickListener listener;

    /**
     * Interfaz para manejar el click sobre una liga.
     */
    public interface OnLigaClickListener {
        void onLigaClick(LigaModel liga);
    }

    /**
     * Constructor del adapter.
     * @param context Contexto de la aplicación.
     * @param listener Listener para manejar el click sobre una liga.
     */
    public LigasCarouselAdapter(Context context, OnLigaClickListener listener) {
        this.context = context;
        this.ligas = new ArrayList<>();
        this.listener = listener;
    }

    /**
     * Actualiza la lista de ligas y refresca la vista.
     * @param ligas Nueva lista de ligas.
     */
    public void setLigas(List<LigaModel> ligas) {
        this.ligas = ligas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LigaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_liga_carousel, parent, false);
        return new LigaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LigaViewHolder holder, int position) {
        LigaModel liga = ligas.get(position);
        holder.bind(liga);
    }

    @Override
    public int getItemCount() {
        return ligas.size();
    }

    /**
     * ViewHolder para los ítems del carrusel de ligas.
     * Contiene referencias a las vistas de cada elemento y gestiona el click.
     */
    class LigaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLogo;
        TextView tvNombre;

        public LigaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.iv_liga_logo);
            tvNombre = itemView.findViewById(R.id.tv_liga_nombre);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onLigaClick(ligas.get(position));
                }
            });
        }

        /**
         * Asigna los datos de la liga a las vistas.
         * @param liga Objeto LigaModel con los datos de la liga.
         */
        public void bind(LigaModel liga) {
            tvNombre.setText(liga.getName());
            Glide.with(context)
                    .load(liga.getLogo())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivLogo);
        }
    }
}