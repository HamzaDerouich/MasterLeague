package com.example.app2.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app2.R;
import com.example.app2.data.UsuarioRepository;
import com.example.app2.model.UsuarioModel;

/**
 * Actividad de ajustes de usuario.
 * Permite al usuario ver y actualizar sus datos personales (nombre, email y contraseña).
 *
 * Funcionalidades principales:
 * - Muestra los datos actuales del usuario en los campos de texto.
 * - Permite modificar nombre, email y contraseña.
 * - Valida los campos antes de actualizar (campos vacíos, formato de email, coincidencia de contraseñas, longitud mínima).
 * - Actualiza los datos en la base de datos local usando UsuarioRepository.
 * - Muestra mensajes de éxito o error según el resultado de la actualización.
 * - Permite volver atrás con el botón de "signup" (cancelar cambios).
 */
public class AjustesActivity extends AppCompatActivity {
    UsuarioRepository accesoDatos;
    EditText nombre, email, password, passwordValidation;
    Button login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        // Ajusta los insets para la barra de estado y navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        accesoDatos = new UsuarioRepository(this);
        nombre = findViewById(R.id.editTextNombre);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        passwordValidation = findViewById(R.id.editTextPasswordValidation);
        login = findViewById(R.id.buttonLoginRegistro);
        signup = findViewById(R.id.buttonSignUpRegistro);

        // Obtiene el usuario actual pasado por intent y muestra sus datos
        UsuarioModel usuario = (UsuarioModel) getIntent().getSerializableExtra("usuario");
        nombre.setText(usuario.getNombre());
        email.setText(usuario.getMail());
        password.setText(usuario.getContrasena());
        passwordValidation.setText(usuario.getContrasena());

        // Botón para actualizar los datos del usuario
        login.setOnClickListener(view -> {
            String nombreString = nombre.getText().toString().trim();
            String emailString = email.getText().toString().trim();
            String passwordString = password.getText().toString().trim();
            String passwordValidationString = passwordValidation.getText().toString().trim();

            // Validaciones de campos
            if (nombreString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty() || passwordValidationString.isEmpty()) {
                Toast.makeText(this, "Rellene todos los campos para continuar.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                Toast.makeText(this, "Ingrese un email válido.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (passwordString.length() < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!passwordString.equals(passwordValidationString)) {
                Mensaje("Error en la actualización", "Las contraseñas no coinciden. Por favor, intente nuevamente.");
                return;
            }

            // Comprueba si el nombre ya está en uso por otro usuario
            UsuarioModel existeUsuario = accesoDatos.consultarUsuario(usuario.getNombre());

            if (existeUsuario != null && !existeUsuario.getMail().equals(emailString)) {
                Mensaje("¡Error en la actualización!", "No se puede cambiar el nombre porque ya está en uso por otro usuario.");
                return;
            }

            // Actualiza los datos del usuario
            boolean actualizacionExitosa = accesoDatos.actualizarDatosUsuario(usuario);

            if (actualizacionExitosa) {
                Mensaje("¡Actualización exitosa!", "Los datos han sido actualizados correctamente.");
            } else {
                Mensaje("¡Error en la actualización!", "No se pudieron actualizar los datos. Inténtelo nuevamente.");
            }
        });

        // Botón para cancelar y volver atrás
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    /**
     * Muestra un mensaje Toast con título y mensaje.
     * @param titulo Título del mensaje.
     * @param mensaje Contenido del mensaje.
     */
    private void Mensaje(String titulo, String mensaje) {
        Toast.makeText(this, titulo + ": " + mensaje, Toast.LENGTH_LONG).show();
    }
}
