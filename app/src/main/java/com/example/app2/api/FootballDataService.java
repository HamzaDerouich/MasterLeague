package com.example.app2.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app2.api.footballDataServiceInterfaces.ClasificacionCallBack;
import com.example.app2.api.footballDataServiceInterfaces.LigasCallback;
import com.example.app2.api.footballDataServiceInterfaces.MaximosGoleadoresCallBack;
import com.example.app2.api.footballDataServiceInterfaces.ResultadosPasadosCallBack;
import com.example.app2.model.ClasificacionModel;
import com.example.app2.model.CountryModel;
import com.example.app2.model.GoalsModel;
import com.example.app2.model.GoleadorModel;
import com.example.app2.model.LigaModel;
import com.example.app2.model.PartidoResultadoModel;
import com.example.app2.model.StatisticsModel;
import com.example.app2.model.TeamModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio para interactuar con la API de football.api-sports.io usando Volley.
 *
 * - Permite obtener ligas, clasificaciones, partidos pasados, próximos partidos y máximos goleadores.
 * - Gestiona la cola de peticiones y el control de solicitudes (pausar, reanudar, cancelar).
 * - Usa callbacks para devolver los resultados de las peticiones.
 * - Incluye métodos auxiliares para parsear los datos recibidos de la API.
 */
public class FootballDataService {
    private static final String TAG = "FootballDataService";
    private static final String API_KEY = "eb81cceb30e5ff6acce3432a513b1659";
    private static final String BASE_URL = "https://v3.football.api-sports.io/";
    private static final int TIMEOUT_MS = 10000;
    private static final int MAX_RETRIES = 3;

    private RequestQueue requestQueue;
    private Context context;
    private boolean requestsPaused = false;

    /**
     * Constructor del servicio.
     * @param context Contexto de la aplicación.
     */
    public FootballDataService(Context context) {
        this.context = context.getApplicationContext();
        this.requestQueue = Volley.newRequestQueue(this.context);
    }

    // Métodos para controlar el estado de las solicitudes
    public void pauseRequests() {
        this.requestsPaused = true;
        requestQueue.cancelAll(TAG);
    }

    public void resumeRequests() {
        this.requestsPaused = false;
    }

    public void cancelPendingRequests() {
        requestQueue.cancelAll(TAG);
    }

    /**
     * Añade una petición a la cola si no están pausadas.
     * @param request Petición JsonObjectRequest a añadir.
     */
    private void addRequest(JsonObjectRequest request) {
        if (requestsPaused) {
            Log.d(TAG, "Requests are paused, not adding new request");
            return;
        }
        request.setTag(TAG);
        request.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_MS,
                MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    /**
     * Obtiene la lista de ligas disponibles.
     * @param callback Callback para manejar el resultado.
     */
    public void obtenerLigas(final LigasCallback callback) {
        String url = BASE_URL + "leagues";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        ArrayList<LigaModel> ligasModel = new ArrayList<>();
                        JSONArray competitions = response.getJSONArray("response");
                        for (int i = 0; i < competitions.length(); i++) {
                            JSONObject comp = competitions.getJSONObject(i);
                            JSONObject leagueObject = comp.getJSONObject("league");
                            JSONObject countryObject = comp.getJSONObject("country");

                            LigaModel liga = new LigaModel();
                            liga.setId(leagueObject.getInt("id"));
                            liga.setName(leagueObject.getString("name"));
                            liga.setLogo(leagueObject.getString("logo"));

                            CountryModel country = new CountryModel();
                            country.setName(countryObject.getString("name"));
                            country.setCode(countryObject.getString("code"));
                            country.setFlag(countryObject.getString("flag"));

                            liga.setCountry(country);
                            ligasModel.add(liga);
                        }
                        callback.onSuccess(ligasModel);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing leagues data", e);
                        callback.onError("Error al procesar los datos de ligas");
                    }
                },
                error -> {
                    Log.e(TAG, "Error getting leagues", error);
                    callback.onError("Error al conectar con el servidor");
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return createHeaders();
            }
        };
        addRequest(request);
    }

    /**
     * Obtiene la clasificación de una liga y temporada.
     * @param leagueId ID de la liga.
     * @param season Temporada.
     * @param callback Callback para manejar el resultado.
     */
    public void obtenerClasificacion(int leagueId, int season, final ClasificacionCallBack callback) {
        String url = BASE_URL + "standings?league=" + leagueId + "&season=" + season;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        ArrayList<ClasificacionModel> resultados = new ArrayList<>();
                        JSONArray responseArray = response.getJSONArray("response");
                        if (responseArray.length() > 0) {
                            JSONObject leagueData = responseArray.getJSONObject(0)
                                    .getJSONObject("league");
                            JSONArray standingsArray = leagueData.getJSONArray("standings");
                            JSONArray groupStandings = standingsArray.getJSONArray(0);
                            for (int i = 0; i < groupStandings.length(); i++) {
                                JSONObject teamObj = groupStandings.getJSONObject(i);
                                ClasificacionModel model = new ClasificacionModel();
                                model.setRank(teamObj.getInt("rank"));
                                JSONObject teamInfo = teamObj.getJSONObject("team");
                                model.setTeamName(teamInfo.getString("name"));
                                model.setTeamLogo(teamInfo.getString("logo"));
                                model.setPoints(teamObj.getInt("points"));
                                model.setGoalsDiff(teamObj.getInt("goalsDiff"));
                                resultados.add(model);
                            }
                        }
                        callback.onSuccess(resultados);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing standings", e);
                        callback.onError("Error al procesar la clasificación");
                    }
                },
                error -> {
                    Log.e(TAG, "Error getting standings", error);
                    callback.onError("Error al obtener la clasificación");
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return createHeaders();
            }
        };
        addRequest(request);
    }

    /**
     * Obtiene los resultados de partidos pasados de una liga y temporada.
     * @param leagueId ID de la liga.
     * @param season Temporada.
     * @param callback Callback para manejar el resultado.
     */
    public void obtenerResultadosPasados(int leagueId, int season, final ResultadosPasadosCallBack callback) {
        String url = BASE_URL + "fixtures?league=" + leagueId + "&season=" + season + "&status=FT";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        List<PartidoResultadoModel> resultados = new ArrayList<>();
                        JSONArray fixturesArray = response.getJSONArray("response");
                        for (int i = 0; i < fixturesArray.length(); i++) {
                            JSONObject fixture = fixturesArray.getJSONObject(i);
                            PartidoResultadoModel partido = parseMatchResult(fixture);
                            resultados.add(partido);
                        }
                        callback.onSuccess(resultados);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing past matches", e);
                        callback.onError("Error al procesar resultados pasados");
                    }
                },
                error -> {
                    Log.e(TAG, "Error getting past matches", error);
                    callback.onError("Error al obtener resultados pasados");
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return createHeaders();
            }
        };
        addRequest(request);
    }

    /**
     * Obtiene los próximos partidos de una liga y temporada.
     * @param leagueId ID de la liga.
     * @param season Temporada.
     * @param callback Callback para manejar el resultado.
     */
    public void obtenerProximosPartidos(int leagueId, int season, final ResultadosPasadosCallBack callback) {
        String url = BASE_URL + "fixtures?league=" + leagueId + "&season=" + season + "&status=NS";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        List<PartidoResultadoModel> resultados = new ArrayList<>();
                        JSONArray fixturesArray = response.getJSONArray("response");
                        for (int i = 0; i < fixturesArray.length(); i++) {
                            JSONObject fixture = fixturesArray.getJSONObject(i);
                            PartidoResultadoModel partido = parseMatchResult(fixture);
                            resultados.add(partido);
                        }
                        callback.onSuccess(resultados);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing upcoming matches", e);
                        callback.onError("Error al procesar próximos partidos");
                    }
                },
                error -> {
                    Log.e(TAG, "Error getting upcoming matches", error);
                    callback.onError("Error al obtener próximos partidos");
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return createHeaders();
            }
        };
        addRequest(request);
    }

    /**
     * Obtiene la lista de máximos goleadores de una liga y temporada.
     * @param leagueId ID de la liga.
     * @param season Temporada.
     * @param callback Callback para manejar el resultado.
     */
    public void obtenerMaximosGoleadores(int leagueId, int season, final MaximosGoleadoresCallBack callback) {
        String url = BASE_URL + "players/topscorers?league=" + leagueId + "&season=" + season;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        ArrayList<GoleadorModel> goleadores = new ArrayList<>();
                        JSONArray playersArray = response.getJSONArray("response");
                        for (int i = 0; i < playersArray.length(); i++) {
                            JSONObject playerObj = playersArray.getJSONObject(i);
                            GoleadorModel goleador = parseTopScorer(playerObj);
                            goleadores.add(goleador);
                        }
                        callback.onSuccess(goleadores);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing top scorers", e);
                        callback.onError("Error al procesar los goleadores");
                    }
                },
                error -> {
                    Log.e(TAG, "Error getting top scorers", error);
                    callback.onError("Error al obtener los goleadores");
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return createHeaders();
            }
        };
        addRequest(request);
    }

    // Métodos auxiliares privados

    /**
     * Crea los headers necesarios para las peticiones a la API.
     * @return Mapa de headers.
     */
    private Map<String, String> createHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("x-apisports-key", API_KEY);
        headers.put("Accept", "application/json");
        return headers;
    }

    /**
     * Parsea un objeto JSON de partido a un modelo PartidoResultadoModel.
     * @param fixture Objeto JSON del partido.
     * @return Modelo PartidoResultadoModel.
     * @throws JSONException Si hay error de parsing.
     */
    private PartidoResultadoModel parseMatchResult(JSONObject fixture) throws JSONException {
        PartidoResultadoModel partido = new PartidoResultadoModel();
        JSONObject teams = fixture.getJSONObject("teams");
        partido.setTeamsHomeName(teams.getJSONObject("home").getString("name"));
        partido.setTeamsAwayName(teams.getJSONObject("away").getString("name"));
        partido.setFixtureDate(fixture.getJSONObject("fixture").getString("date"));
        if (fixture.has("goals")) {
            JSONObject goals = fixture.getJSONObject("goals");
            partido.setGoalsHome(goals.getInt("home"));
            partido.setGoalsAway(goals.getInt("away"));
        }
        return partido;
    }

    /**
     * Parsea un objeto JSON de goleador a un modelo GoleadorModel.
     * @param playerObj Objeto JSON del goleador.
     * @return Modelo GoleadorModel.
     * @throws JSONException Si hay error de parsing.
     */
    private GoleadorModel parseTopScorer(JSONObject playerObj) throws JSONException {
        GoleadorModel goleador = new GoleadorModel();
        JSONObject playerData = playerObj.getJSONObject("player");
        JSONArray statisticsArray = playerObj.getJSONArray("statistics");

        goleador.setName(playerData.getString("name"));
        goleador.setPhoto(playerData.getString("photo"));

        StatisticsModel[] statisticsModels = new StatisticsModel[statisticsArray.length()];

        for (int j = 0; j < statisticsArray.length(); j++) {
            JSONObject statistics = statisticsArray.getJSONObject(j);
            StatisticsModel statsModel = new StatisticsModel();

            if (statistics.has("team")) {
                JSONObject team = statistics.getJSONObject("team");
                TeamModel teamModel = new TeamModel();
                teamModel.setName(team.getString("name"));
                statsModel.setTeam(teamModel);
            }

            if (statistics.has("goals")) {
                JSONObject goals = statistics.getJSONObject("goals");
                GoalsModel goalsModel = new GoalsModel();
                goalsModel.setTotal(goals.getInt("total"));
                statsModel.setGoals(goalsModel);
            }

            statisticsModels[j] = statsModel;
        }

        goleador.setStatistics(statisticsModels);
        return goleador;
    }
}