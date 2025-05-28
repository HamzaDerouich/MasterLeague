package com.example.app2.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app2.model.Liga;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FootballApiService {
    private static final String API_KEY = "9a7a3abb6ae344ce84837c14800ef69f";
    private static final String BASE_URL = "https://api.football-data.org/v4/";

    private RequestQueue requestQueue;
    private Context context;

    private static ArrayList<Liga> ligas = new ArrayList<>();

    public FootballApiService(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public interface LigasCallback {
        void onSuccess(ArrayList<Liga> ligas);
        void onError(String error);
    }

    public void obtenerLigas(final LigasCallback callback) {

        String url = BASE_URL + "competitions?plan=TIER_ONE";

        if( ligas.size() != 0 )
        {
            callback.onSuccess(ligas);

        }
        else
        {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray competitions = response.getJSONArray("competitions");
                                //ArrayList<Liga> ligas = new ArrayList<>();
                                for (int i = 0; i < competitions.length(); i++) {
                                    JSONObject comp = competitions.getJSONObject(i);
                                    Liga liga = new Liga(
                                            comp.getString("id"),
                                            comp.getString("name"),
                                            obtenerUrlBandera(comp.getJSONObject("area").getString("code")),
                                            comp.optString("currentSeason", ""),
                                            comp.getJSONObject("area").getString("name"),
                                            comp.optString("founded", "Desconocido"),
                                            comp.getJSONObject("area").getString("code")
                                    );

                                    ligas.add(liga);

                                }

                                callback.onSuccess(ligas);

                            } catch (JSONException e) {
                                callback.onError("Error al procesar los datos");
                                Log.e("API Error", e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            callback.onError("Error al conectar con el servidor, error: ");
                            Log.e("API Error", error.getMessage());
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("X-Auth-Token", API_KEY);
                    return headers;
                }
            };

            requestQueue.add(request);

        }

    }

    private String obtenerUrlBandera(String countryCode) {
        // Usamos CountryFlags API para las banderas
        return "https://countryflagsapi.com/png/" + countryCode;
    }
}