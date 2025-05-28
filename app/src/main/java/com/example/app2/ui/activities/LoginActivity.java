package com.example.app2.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app2.MainActivity;
import com.example.app2.R;
import com.example.app2.data.UsuarioRepository;
import com.example.app2.model.UsuarioModel;

/**
 * Actividad de inicio de sesión para la aplicación MasterLeague.
 *
 * Funcionalidades principales:
 * - Permite al usuario iniciar sesión ingresando nombre, email y contraseña.
 * - Valida los campos de entrada (no vacíos y formato de email).
 * - Verifica las credenciales contra la base de datos local usando UsuarioRepository.
 * - Muestra mensajes de error si los datos son incorrectos o el usuario no existe.
 * - Permite navegar a la pantalla de registro o recuperación de contraseña.
 * - Si el login es exitoso, navega a la pantalla principal (MainActivity) pasando el usuario autenticado.
 */
public class LoginActivity extends AppCompatActivity {

    private UsuarioRepository usuarioRepository;
    private EditText etNombre, etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupListeners();
    }

    /**
     * Inicializa las vistas y el repositorio de usuarios.
     */
    private void initViews() {
        usuarioRepository = new UsuarioRepository(this);
        etNombre = findViewById(R.id.editTextNombre);
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
    }

    /**
     * Configura los listeners para los botones de login, registro y recuperación de contraseña.
     */
    private void setupListeners() {
        TextView tvForgotPassword = findViewById(R.id.textView8);
        Button btnLogin = findViewById(R.id.buttonLogin);
        Button btnSignUp = findViewById(R.id.buttonSignUp);

        tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(this, RecuperarContrasenaActivity.class));
            Toast.makeText(this, "Todos cometemos errores", Toast.LENGTH_SHORT).show();
        });

        btnSignUp.setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class)));

        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    /**
     * Intenta realizar el login validando los datos y comprobando las credenciales.
     */
    private void attemptLogin() {
        String nombre = etNombre.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!validateLoginInput(nombre, email, password)) {
            return;
        }

        UsuarioModel usuario = usuarioRepository.consultarUsuario(nombre);

        if (usuario == null) {
            showToast("Usuario no encontrado");
            return;
        }

        if (validateUserCredentials(usuario, nombre, email, password)) {
            navigateToMainActivity(usuario);
        } else {
            showToast("Credenciales incorrectas");
        }
    }

    /**
     * Valida los campos de entrada del login.
     * @return true si los datos son válidos, false en caso contrario.
     */
    private boolean validateLoginInput(String nombre, String email, String password) {
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showToast("Complete todos los campos");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Email no válido");
            return false;
        }

        return true;
    }

    /**
     * Verifica si las credenciales ingresadas coinciden con las del usuario almacenado.
     */
    private boolean validateUserCredentials(UsuarioModel usuario, String nombre,
                                            String email, String password) {
        return nombre.equals(usuario.getNombre()) &&
                email.equals(usuario.getMail()) &&
                password.equals(usuario.getContrasena());
    }

    /**
     * Navega a la pantalla principal pasando el usuario autenticado.
     */
    private void navigateToMainActivity(UsuarioModel usuario) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
        finish();
    }

    /**
     * Muestra un mensaje Toast en pantalla.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}