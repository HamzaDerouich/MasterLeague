package com.example.app2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app2.R;
import com.example.app2.model.LigaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter para mostrar un listado horizontal de ligas en la sección de goleadores.
 * Utiliza el modelo LigaModel para cada ítem.
 *
 * - Muestra el logo y el nombre de cada liga.
 * - Usa Glide para cargar el logo de la liga.
 * - Permite resaltar la liga seleccionada.
 * - Permite manejar el evento de click sobre una liga mediante la interfaz OnLigaClickListener.
 * - El layout de cada ítem es item_liga_goleadores.xml.
 */
public class LigasGoleadoresAdapter extends RecyclerView.Adapter<LigasGoleadoresAdapter.LigaViewHolder> {

    private Context context;
    private List<LigaModel> ligas;
    private OnLigaClickListener listener;
    private int selectedPosition = 0;

    /**
     * Interfaz para manejar el click sobre una liga.
     */
    public interface OnLigaClickListener {
        void onLigaClick(LigaModel liga, int position);
    }

    /**
     * Constructor del adapter.
     * @param context Contexto de la aplicación.
     * @param listener Listener para manejar el click sobre una liga.
     */
    public LigasGoleadoresAdapter(Context context, OnLigaClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.ligas = new ArrayList<>();
    }

    /**
     * Actualiza la lista de ligas y refresca la vista.
     * @param ligas Nueva lista de ligas.
     */
    public void setLigas(List<LigaModel> ligas) {
        this.ligas = ligas;
        notifyDataSetChanged();
    }

    /**
     * Cambia la posición seleccionada y refresca la vista.
     * @param position Nueva posición seleccionada.
     */
    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LigaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_liga_goleadores, parent, false);
        return new LigaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LigaViewHolder holder, int position) {
        LigaModel liga = ligas.get(position);

        holder.tvNombreLiga.setText(liga.getName());
        Glide.with(context)
                .load(liga.getLogo())
                .placeholder(R.drawable.ic_league)
                .into(holder.ivLogoLiga);

        // Resaltar la liga seleccionada
        if (position == selectedPosition) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, com.google.android.material.R.color.design_default_color_on_primary));
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardBackground));
        }

        holder.cardView.setOnClickListener(v -> {
            listener.onLigaClick(liga, position);
        });
    }

    @Override
    public int getItemCount() {
        return ligas.size();
    }

    /**
     * ViewHolder para los ítems de la lista de ligas.
     * Contiene referencias a las vistas de cada elemento.
     */
    static class LigaViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivLogoLiga;
        TextView tvNombreLiga;

        public LigaViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_liga);
            ivLogoLiga = itemView.findViewById(R.id.iv_logo_liga);
            tvNombreLiga = itemView.findViewById(R.id.tv_nombre_liga);
        }
    }
}