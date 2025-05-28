package com.example.app2.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Clase para la gestión de la base de datos local SQLite de la aplicación MasterLeague.
 * Extiende SQLiteOpenHelper para crear y actualizar las tablas necesarias.
 *
 * Tablas gestionadas:
 * - usuarios: almacena los datos de los usuarios registrados.
 * - favoritos_ligas: almacena las ligas marcadas como favoritas por el usuario.
 *
 * Métodos principales:
 * - onCreate: crea las tablas al inicializar la base de datos.
 * - onUpgrade: elimina y recrea las tablas si se detecta un cambio de versión.
 */
public class MasterLegueBD extends SQLiteOpenHelper {

    private Context contexto;

    // Sentencias SQL para crear las tablas
    private static final String TABLA_USUARIOS = "CREATE TABLE usuarios(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT, " +
            "email TEXT, " +
            "password TEXT)";
    private static final String TABLA_FAVORITOS_LIGAS = "CREATE TABLE favoritos_ligas(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "liga_id INTEGER, " +
            "nombre TEXT, " +
            "logo_url TEXT, " +
            "temporada INTEGER)";

    // Sentencias SQL para eliminar las tablas
    private static final String ELIMINAR_USUARIOS = "DROP TABLE IF EXISTS usuarios";
    private static final String ELIMINAR_FAVORITOS_LIGAS = "DROP TABLE IF EXISTS favoritos_ligas";

    /**
     * Constructor de la base de datos.
     * @param context Contexto de la aplicación.
     */
    public MasterLegueBD(Context context) {
        super(context, "MasterLeague", null, 1);
        this.contexto = context;
    }

    /**
     * Crea las tablas de la base de datos al inicializarla por primera vez.
     * @param sqLiteDatabase Instancia de la base de datos.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(TABLA_USUARIOS);
            sqLiteDatabase.execSQL(TABLA_FAVORITOS_LIGAS);
        } catch (SQLException e) {
            Toast.makeText(contexto, "Error al crear la base de datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la base de datos si cambia la versión.
     * Elimina las tablas existentes y las vuelve a crear.
     * @param sqLiteDatabase Instancia de la base de datos.
     * @param oldVersion Versión anterior.
     * @param newVersion Nueva versión.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            sqLiteDatabase.execSQL(ELIMINAR_USUARIOS);
            sqLiteDatabase.execSQL(ELIMINAR_FAVORITOS_LIGAS);
            onCreate(sqLiteDatabase);
        } catch (SQLException e) {
            Toast.makeText(contexto, "Error al actualizar la base de datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}