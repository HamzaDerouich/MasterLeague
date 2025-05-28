package com.example.app2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app2.R;
import com.example.app2.model.ClasificacionModel;

import java.util.List;

/**
 * Adapter para mostrar la clasificación de equipos en un RecyclerView.
 * Utiliza el modelo ClasificacionModel para cada ítem.
 *
 * - Muestra posición, nombre, logo, puntos y diferencia de goles de cada equipo.
 * - Usa Glide para cargar el logo del equipo.
 * - El layout de cada ítem es item_standing.xml.
 */
public class ClasificacionAdapter extends RecyclerView.Adapter<ClasificacionAdapter.StandingViewHolder> {

    // Lista de objetos ClasificacionModel que representan la clasificación
    private List<ClasificacionModel> standings;

    /**
     * Constructor del adapter.
     * @param standings Lista inicial de clasificaciones.
     */
    public ClasificacionAdapter(List<ClasificacionModel> standings) {
        this.standings = standings;
    }

    /**
     * Actualiza la lista de clasificaciones y refresca la vista.
     * @param newStandings Nueva lista de clasificaciones.
     */
    public void updateData(List<ClasificacionModel> newStandings) {
        this.standings = newStandings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StandingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout del ítem de clasificación
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_standing, parent, false);
        return new StandingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StandingViewHolder holder, int position) {
        // Obtiene el objeto de clasificación para la posición actual
        ClasificacionModel standing = standings.get(position);

        // Asigna los valores a las vistas del ViewHolder
        holder.rank.setText(String.valueOf(standing.getRank()));
        holder.teamName.setText(standing.getTeamName());
        holder.points.setText(String.valueOf(standing.getPoints()));
        holder.goalsDiff.setText(String.valueOf(standing.getGoalsDiff()));

        // Carga el logo del equipo usando Glide
        Glide.with(holder.itemView.getContext())
                .load(standing.getTeamLogo())
                .placeholder(R.drawable.ic_team)
                .into(holder.teamLogo);
    }

    @Override
    public int getItemCount() {
        // Devuelve el número de elementos en la lista
        return standings != null ? standings.size() : 0;
    }

    /**
     * ViewHolder para los ítems de la clasificación.
     * Contiene referencias a las vistas de cada elemento.
     */
    static class StandingViewHolder extends RecyclerView.ViewHolder {
        TextView rank;
        ImageView teamLogo;
        TextView teamName;
        TextView points;
        TextView goalsDiff;

        public StandingViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.position);
            teamLogo = itemView.findViewById(R.id.team_logo);
            teamName = itemView.findViewById(R.id.team_name);
            points = itemView.findViewById(R.id.points);
            goalsDiff = itemView.findViewById(R.id.goal_diff);
        }
    }
}