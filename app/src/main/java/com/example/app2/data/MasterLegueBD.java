package com.example.app2;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MasterLegueBD extends SQLiteOpenHelper
{
    Context contexto;
    static String tabla_usuarios = "CREATE TABLE usuarios(id INTEGER primary key autoincrement , nombre TEXT , email TEXT , password  TEXT )";
    static String eliminar_db = "DROP TABLE IF EXISTS usuarios";
    public MasterLegueBD(Context context) {
        super(context, "MasterLeague", null, 1);
        this.contexto = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        try
        {
            sqLiteDatabase.execSQL(tabla_usuarios);
        }catch (SQLException e)
        {
            Toast.makeText(contexto, "Error al crear la base de datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        try
        {
            sqLiteDatabase.execSQL(eliminar_db);
        }catch (SQLException e)
        {
            Toast.makeText(contexto, "Error al eliminar la base de datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
