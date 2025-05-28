package com.example.app2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.app2.model.Usuario;

public class AccesoDatos {

    private Context contexto;
    private MasterLegueBD masterLegueBD;

    // Constructor
    public AccesoDatos(Context contexto) {
        this.contexto = contexto;
        masterLegueBD = new MasterLegueBD(this.contexto);
    }

    // Consultar un usuario por nombre
    public Usuario consultarUsuario(String nombre) {
        SQLiteDatabase accesoLectura = null;
        Usuario usuario = null;

        try {
            accesoLectura = masterLegueBD.getReadableDatabase();

            String[] campos = new String[]{"nombre", "email", "password"};
            String seleccion = "nombre = ?";
            String[] argumentos = new String[]{nombre};

            Cursor c = accesoLectura.query("usuarios", campos, seleccion, argumentos, null, null, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    usuario = new Usuario();
                    usuario.setNombre(c.getString(0));
                    usuario.setMail(c.getString(1));
                    usuario.setContaseña(c.getString(2));
                }
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Manejo de errores
        } finally {
            if (accesoLectura != null) {
                accesoLectura.close();
            }
        }

        return usuario;
    }

    // Registrar un nuevo usuario
    public boolean registrarUsuario(Usuario usuario) {
        SQLiteDatabase accesoEscritura = null;
        boolean exito = false;

        try {
            accesoEscritura = masterLegueBD.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("nombre", usuario.getNombre());
            registro.put("email", usuario.getMail());
            registro.put("password", usuario.getContaseña());

            long resultado = accesoEscritura.insert("usuarios", null, registro);
            exito = (resultado != -1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (accesoEscritura != null) {
                accesoEscritura.close();
            }
        }

        return exito;
    }

    // Actualizar los datos de un usuario
    public boolean actualizarDatosUsuario(Usuario usuario) {
        SQLiteDatabase accesoEscritura = null;
        boolean exito = false;

        try {
            accesoEscritura = masterLegueBD.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", usuario.getNombre());
            contentValues.put("email", usuario.getMail());
            contentValues.put("password", usuario.getContaseña());

            long filasAfectadas = accesoEscritura.update("usuarios", contentValues, "nombre = ?", new String[]{usuario.getNombre()});
            exito = (filasAfectadas > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (accesoEscritura != null) {
                accesoEscritura.close();
            }
        }

        return exito;
    }
}
