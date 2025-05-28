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
import com.example.app2.model.PartidoResultadoModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Adapter para mostrar una lista de partidos en un RecyclerView.
 * Utiliza el modelo PartidoResultadoModel para cada ítem.
 *
 * - Muestra los nombres y logos de los equipos local y visitante.
 * - Muestra la fecha del partido formateada.
 * - Puede mostrar u ocultar el marcador según el parámetro showScores.
 * - Usa Glide para cargar los logos de los equipos.
 * - El layout de cada ítem es item_match.xml.
 */
public class PartidosAdapter extends RecyclerView.Adapter<PartidosAdapter.MatchViewHolder> {

    private List<PartidoResultadoModel> matches;
    private boolean showScores;
    private SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    private SimpleDateFormat outputFormat = new SimpleDateFormat("EEE, dd MMM yyyy - HH:mm", Locale.getDefault());

    /**
     * Constructor del adapter.
     * @param matches Lista de partidos.
     * @param showScores Indica si se debe mostrar el marcador.
     */
    public PartidosAdapter(List<PartidoResultadoModel> matches, boolean showScores) {
        this.matches = matches;
        this.showScores = showScores;
    }

    /**
     * Actualiza la lista de partidos y refresca la vista.
     * @param newMatches Nueva lista de partidos.
     */
    public void updateData(List<PartidoResultadoModel> newMatches) {
        this.matches = newMatches != null ? newMatches : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        PartidoResultadoModel match = matches.get(position);

        // Configurar nombres de equipos
        holder.homeTeam.setText(match.getTeamsHomeName());
        holder.awayTeam.setText(match.getTeamsAwayName());

        // Configurar logos (usando Glide)
        Glide.with(holder.itemView.getContext())
                .load(match.getHomeTeamLogo())
                .placeholder(R.drawable.ic_team)
                .into(holder.homeLogo);

        Glide.with(holder.itemView.getContext())
                .load(match.getAwayTeamLogo())
                .placeholder(R.drawable.ic_team)
                .into(holder.awayLogo);

        // Formatear fecha
        try {
            Date date = inputFormat.parse(match.getFixtureDate());
            holder.date.setText(outputFormat.format(date));
        } catch (ParseException e) {
            holder.date.setText(match.getFixtureDate());
        }

        // Mostrar u ocultar marcador según corresponda
        if (showScores) {
            holder.score.setText(String.format("%d - %d", match.getGoalsHome(), match.getGoalsAway()));
            holder.score.setVisibility(View.VISIBLE);
        } else {
            holder.score.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    /**
     * ViewHolder para los ítems de partidos.
     * Contiene referencias a las vistas de cada elemento.
     */
    static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView homeTeam;
        TextView awayTeam;
        TextView score;
        TextView date;
        ImageView homeLogo;
        ImageView awayLogo;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTeam = itemView.findViewById(R.id.home_team);
            awayTeam = itemView.findViewById(R.id.away_team_name);
            score = itemView.findViewById(R.id.score);
            date = itemView.findViewById(R.id.match_date);
            homeLogo = itemView.findViewById(R.id.home_team_logo);
            awayLogo = itemView.findViewById(R.id.away_team_logo);
        }
    }
}