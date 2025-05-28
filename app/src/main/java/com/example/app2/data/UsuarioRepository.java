package com.example.app2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.app2.model.UsuarioModel;

/**
 * Repositorio para gestionar las operaciones CRUD de usuarios en la base de datos local SQLite.
 *
 * - Permite consultar usuarios por nombre o email.
 * - Permite registrar nuevos usuarios.
 * - Permite actualizar los datos de un usuario.
 * - Utiliza la tabla "usuarios" de la base de datos.
 */
public class UsuarioRepository {

    private Context contexto;
    private MasterLegueBD masterLegueBD;

    /**
     * Constructor del repositorio.
     * @param contexto Contexto de la aplicación.
     */
    public UsuarioRepository(Context contexto) {
        this.contexto = contexto;
        masterLegueBD = new MasterLegueBD(this.contexto);
    }

    /**
     * Consulta un usuario por su nombre.
     * @param nombre Nombre del usuario a buscar.
     * @return UsuarioModel con los datos del usuario, o null si no existe.
     */
    public UsuarioModel consultarUsuario(String nombre) {
        SQLiteDatabase accesoLectura = null;
        UsuarioModel usuario = null;

        try {
            accesoLectura = masterLegueBD.getReadableDatabase();

            String[] campos = new String[]{"nombre", "email", "password"};
            String seleccion = "nombre = ?";
            String[] argumentos = new String[]{nombre};

            Cursor c = accesoLectura.query("usuarios", campos, seleccion, argumentos, null, null, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    usuario = new UsuarioModel();
                    usuario.setNombre(c.getString(0));
                    usuario.setMail(c.getString(1));
                    usuario.setContrasena(c.getString(2));
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

    /**
     * Consulta un usuario por su email.
     * @param email Email del usuario a buscar.
     * @return UsuarioModel con los datos del usuario, o null si no existe.
     */
    public UsuarioModel consultarUsuarioPorEmail(String email) {
        SQLiteDatabase db = null;
        UsuarioModel usuario = null;
        try {
            db = masterLegueBD.getReadableDatabase();
            String[] campos = new String[]{"nombre", "email", "password"};
            String seleccion = "email = ?";
            String[] argumentos = new String[]{email};
            Cursor c = db.query("usuarios", campos, seleccion, argumentos, null, null, null);
            if (c != null && c.moveToFirst()) {
                usuario = new UsuarioModel();
                usuario.setNombre(c.getString(0));
                usuario.setMail(c.getString(1));
                usuario.setContrasena(c.getString(2));
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
        return usuario;
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * @param usuario Objeto UsuarioModel con los datos del usuario.
     * @return true si se registró correctamente, false si hubo error.
     */
    public boolean registrarUsuario(UsuarioModel usuario) {
        SQLiteDatabase accesoEscritura = null;
        boolean exito = false;

        try {
            accesoEscritura = masterLegueBD.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("nombre", usuario.getNombre());
            registro.put("email", usuario.getMail());
            registro.put("password", usuario.getContrasena());

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

    /**
     * Actualiza los datos de un usuario existente.
     * @param usuario Objeto UsuarioModel con los datos actualizados.
     * @return true si se actualizó correctamente, false si hubo error.
     */
    public boolean actualizarDatosUsuario(UsuarioModel usuario) {
        SQLiteDatabase accesoEscritura = null;
        boolean exito = false;

        try {
            accesoEscritura = masterLegueBD.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", usuario.getNombre());
            contentValues.put("email", usuario.getMail());
            contentValues.put("password", usuario.getContrasena());

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
