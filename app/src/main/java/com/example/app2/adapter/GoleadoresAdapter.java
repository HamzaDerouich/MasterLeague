package com.example.app2.adapter;

import android.content.Context;
import android.util.Log;
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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.app2.R;
import com.example.app2.model.GoleadorModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter para mostrar la lista de goleadores en un RecyclerView.
 * Utiliza el modelo GoleadorModel para cada ítem.
 *
 * - Muestra la posición, nombre, foto, logo del equipo, goles y asistencias de cada jugador.
 * - Usa Glide para cargar imágenes de jugadores y equipos.
 * - Permite personalizar si se muestran los logos de equipo y las asistencias.
 * - El layout de cada ítem es item_top_scorer.xml.
 */
public class GoleadoresAdapter extends RecyclerView.Adapter<GoleadoresAdapter.TopScorerViewHolder> {

    private static final String TAG = "GoleadoresAdapter";
    private List<GoleadorModel> topScorers;
    private Context context;
    private boolean showTeamLogo;
    private boolean showAssists;

    /**
     * Constructor principal del adapter.
     * @param topScorers Lista de goleadores.
     * @param showTeamLogo Indica si se debe mostrar el logo del equipo.
     * @param showAssists Indica si se deben mostrar las asistencias.
     */
    public GoleadoresAdapter(List<GoleadorModel> topScorers, boolean showTeamLogo, boolean showAssists) {
        this.topScorers = topScorers != null ? topScorers : new ArrayList<>();
        this.showTeamLogo = showTeamLogo;
        this.showAssists = showAssists;
    }

    /**
     * Constructor secundario que por defecto muestra logo de equipo y asistencias.
     * @param topScorers Lista de goleadores.
     */
    public GoleadoresAdapter(List<GoleadorModel> topScorers) {
        this(topScorers, true, true);
    }

    @NonNull
    @Override
    public TopScorerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_top_scorer, parent, false);
        return new TopScorerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopScorerViewHolder holder, int position) {
        try {
            GoleadorModel scorer = topScorers.get(position);

            // Estilo de la posición (oro, plata, bronce, etc.)
            setupPositionStyle(holder, position);

            // Nombre del jugador
            holder.playerName.setText(scorer.getName() != null ? scorer.getName() : "-");

            // Estadísticas de goles y asistencias
            int goals = 0;
            int assists = 0;
            if (scorer.getStatistics() != null && scorer.getStatistics().length > 0 && scorer.getStatistics()[0] != null) {
                if (scorer.getStatistics()[0].getGoals() != null) {
                    goals = scorer.getStatistics()[0].getGoals().getTotal();
                    assists = scorer.getStatistics()[0].getGoals().getAssists();
                }
            }
            holder.goals.setText(String.valueOf(goals));

            if (showAssists) {
                holder.assists.setText(String.valueOf(assists));
                holder.assists.setVisibility(View.VISIBLE);
                holder.assistsLabel.setVisibility(View.VISIBLE);
            } else {
                holder.assists.setVisibility(View.GONE);
                holder.assistsLabel.setVisibility(View.GONE);
            }

            // Logo del equipo
            if (showTeamLogo && scorer.getStatistics() != null && scorer.getStatistics().length > 0
                    && scorer.getStatistics()[0].getTeam() != null
                    && scorer.getStatistics()[0].getTeam().getLogo() != null) {
                Glide.with(context)
                        .load(scorer.getStatistics()[0].getTeam().getLogo())
                        .placeholder(R.drawable.ic_team)
                        .error(R.drawable.ic_team)
                        .into(holder.teamLogo);
                holder.teamLogo.setVisibility(View.VISIBLE);
            } else {
                holder.teamLogo.setVisibility(View.GONE);
            }

            // Foto del jugador
            if (scorer.getPhoto() != null && !scorer.getPhoto().isEmpty()) {
                Glide.with(context)
                        .load(scorer.getPhoto())
                        .placeholder(R.drawable.ic_player)
                        .error(R.drawable.ic_player)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .circleCrop()
                        .into(holder.playerPhoto);
            } else {
                holder.playerPhoto.setImageResource(R.drawable.ic_player);
            }

        } catch (Exception e) {
            Log.e(TAG, "Error binding view at position " + position + ": " + e.getMessage());
            holder.playerName.setText("Error cargando datos");
            holder.goals.setText("0");
            holder.assists.setText("0");
            holder.playerPhoto.setImageResource(R.drawable.ic_player);
        }
    }

    /**
     * Aplica estilos especiales a las posiciones destacadas (oro, plata, bronce).
     * @param holder ViewHolder del ítem.
     * @param position Posición en la lista.
     */
    private void setupPositionStyle(@NonNull TopScorerViewHolder holder, int position) {
        holder.position.setText(String.valueOf(position + 1));
        holder.position.setTextColor(ContextCompat.getColor(context, R.color.textSecondary));
        holder.position.setBackgroundResource(0);
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardBackground));

        switch (position) {
            case 0:
                holder.position.setTextColor(ContextCompat.getColor(context, R.color.gold));
                holder.position.setBackgroundResource(R.drawable.bg_position_first);
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardHighlight));
                break;
            case 1:
                holder.position.setTextColor(ContextCompat.getColor(context, R.color.silver));
                holder.position.setBackgroundResource(R.drawable.bg_position_second);
                break;
            case 2:
                holder.position.setTextColor(ContextCompat.getColor(context, R.color.bronze));
                holder.position.setBackgroundResource(R.drawable.bg_postion_third);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return topScorers.size();
    }

    /**
     * Actualiza la lista de goleadores y refresca la vista.
     * @param newTopScorers Nueva lista de goleadores.
     */
    public void updateData(List<GoleadorModel> newTopScorers) {
        this.topScorers = newTopScorers != null ? newTopScorers : new ArrayList<>();
        notifyDataSetChanged();
    }

    /**
     * Método alternativo para actualizar la lista de goleadores.
     * @param goleadores Nueva lista de goleadores.
     */
    public void actualizarLista(ArrayList<GoleadorModel> goleadores) {
        updateData(goleadores);
    }

    /**
     * ViewHolder para los ítems de goleadores.
     * Contiene referencias a las vistas de cada elemento.
     */
    static class TopScorerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView position;
        ImageView playerPhoto;
        TextView playerName;
        ImageView teamLogo;
        TextView goals;
        TextView assists;
        TextView assistsLabel;

        public TopScorerViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            position = itemView.findViewById(R.id.tv_position);
            playerPhoto = itemView.findViewById(R.id.iv_player_photo);
            playerName = itemView.findViewById(R.id.tv_player_name);
            teamLogo = itemView.findViewById(R.id.iv_team_logo);
            goals = itemView.findViewById(R.id.tv_goals);
            assists = itemView.findViewById(R.id.tv_assists);
            assistsLabel = itemView.findViewById(R.id.tv_assists_label);
        }
    }
}