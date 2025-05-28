package com.example.app2.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app2.model.NoticiaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para obtener noticias deportivas usando la API de mediastack y Volley.
 *
 * - Realiza peticiones HTTP para obtener noticias de deportes en español.
 * - Parsea la respuesta JSON y la convierte en una lista de objetos NoticiaModel.
 * - Usa un callback para devolver los resultados o errores de forma asíncrona.
 */
public class NewsDataService
{
    private static final String API_KEY = "c720919786725b04b372e9e48637d8fa";
    private static final String BASE_URL = "https://api.mediastack.com/v1/";

    private RequestQueue requestQueue;
    private Context context;

    /**
     * Constructor del servicio.
     * @param context Contexto de la aplicación.
     */
    public NewsDataService(Context context)
    {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Interfaz de callback para manejar la respuesta de las noticias.
     */
    public interface NewsCallback {
        void onSuccess(ArrayList<NoticiaModel> noticias);
        void onError(String error);
    }

    /**
     * Obtiene las noticias deportivas en español desde la API de mediastack.
     * @param callback Callback para manejar el resultado o error.
     */
    public void obtenerNoticias(final NewsDataService.NewsCallback callback) {

        String url = BASE_URL + "news?access_key=" + API_KEY + "&languages=es&categories=sports";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray news = response.getJSONArray("data");
                            ArrayList<NoticiaModel> noticiaArrayLists = new ArrayList<>();
                            for (int i = 0; i < news.length(); i++) {
                                JSONObject item = news.getJSONObject(i);
                                NoticiaModel noticia = new NoticiaModel(
                                        item.optString("author", "Desconocido"),
                                        item.optString("title", "Sin título"),
                                        item.optString("description", "Sin descripción"),
                                        item.optString("url", ""),
                                        item.optString("source", ""),
                                        item.optString("image", null),
                                        item.optString("category", ""),
                                        item.optString("language", ""),
                                        item.optString("country", ""),
                                        item.optString("published_at", "")
                                );
                                noticiaArrayLists.add(noticia);
                            }

                            callback.onSuccess(noticiaArrayLists);

                        } catch (JSONException e) {
                            callback.onError("Error al procesar los datos");
                            Log.e("API Error", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError("Error al conectar con el servidor: " + error.getMessage());
                        Log.e("API Error", error.toString());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                return new HashMap<>();
            }
        };

        requestQueue.add(request);
    }
}


