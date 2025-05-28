package com.example.app2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.app2.model.FavoritoLigaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar las ligas favoritas del usuario en la base de datos local SQLite.
 *
 * - Permite agregar, obtener, eliminar y comprobar si una liga es favorita.
 * - Utiliza la tabla "favoritos_ligas" para almacenar la información.
 * - Cada favorito se representa con un objeto FavoritoLigaModel.
 */
public class FavoritosRepository {
    private MasterLegueBD dbHelper;

    /**
     * Constructor del repositorio.
     * @param context Contexto de la aplicación.
     */
    public FavoritosRepository(Context context) {
        dbHelper = new MasterLegueBD(context);
    }

    /**
     * Agrega una liga a favoritos si no existe previamente.
     * @param liga Objeto FavoritoLigaModel con los datos de la liga.
     * @return true si se agregó correctamente, false si ya existía o hubo error.
     */
    public boolean agregarFavorito(FavoritoLigaModel liga) {
        SQLiteDatabase db = null;
        boolean exito = false;
        try {
            db = dbHelper.getWritableDatabase();
            // Verificar si ya existe
            Cursor c = db.query("favoritos_ligas", null, "liga_id = ?", new String[]{String.valueOf(liga.getLigaId())}, null, null, null);
            boolean existe = (c != null && c.moveToFirst());
            if (c != null) c.close();
            if (existe) {
                exito = false; // Ya existe, no agregar
            } else {
                ContentValues values = new ContentValues();
                values.put("liga_id", liga.getLigaId());
                values.put("nombre", liga.getNombre());
                values.put("logo_url", liga.getLogoUrl());
                values.put("temporada", liga.getTemporada());
                long res = db.insert("favoritos_ligas", null, values);
                exito = (res != -1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
        return exito;
    }

    /**
     * Obtiene la lista de ligas favoritas almacenadas en la base de datos.
     * @return Lista de objetos FavoritoLigaModel.
     */
    public List<FavoritoLigaModel> obtenerFavoritos() {
        List<FavoritoLigaModel> lista = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor c = db.query("favoritos_ligas", null, null, null, null, null, null);
            if (c != null) {
                while (c.moveToNext()) {
                    lista.add(new FavoritoLigaModel(
                            c.getInt(c.getColumnIndexOrThrow("liga_id")),
                            c.getString(c.getColumnIndexOrThrow("nombre")),
                            c.getString(c.getColumnIndexOrThrow("logo_url")),
                            c.getInt(c.getColumnIndexOrThrow("temporada"))
                    ));
                }
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
        return lista;
    }

    /**
     * Elimina una liga de favoritos por su ID.
     * @param ligaId ID de la liga a eliminar.
     * @return true si se eliminó correctamente, false si hubo error.
     */
    public boolean eliminarFavoritoPorLigaId(int ligaId) {
        SQLiteDatabase db = null;
        boolean exito = false;
        try {
            db = dbHelper.getWritableDatabase();
            int filas = db.delete("favoritos_ligas", "liga_id = ?", new String[]{String.valueOf(ligaId)});
            exito = filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
        return exito;
    }

    /**
     * Comprueba si una liga está marcada como favorita.
     * @param ligaId ID de la liga a comprobar.
     * @return true si la liga es favorita, false en caso contrario.
     */
    public boolean esFavorito(int ligaId) {
        SQLiteDatabase db = null;
        boolean existe = false;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor c = db.query("favoritos_ligas", null, "liga_id = ?", new String[]{String.valueOf(ligaId)}, null, null, null);
            existe = (c != null && c.moveToFirst());
            if (c != null) c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
        return existe;
    }
}