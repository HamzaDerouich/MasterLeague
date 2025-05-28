package com.example.app2.ui;

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
import com.example.app2.model.Usuario;

public class AjustesActivity extends AppCompatActivity {
    UsuarioRepository accesoDatos;
    EditText nombre, email, password, passwordValidation;
    Button login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

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

        // Usuario

        Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        nombre.setText(usuario.getNombre());
        email.setText(usuario.getMail());
        password.setText(usuario.getContaseña());
        passwordValidation.setText(usuario.getContaseña());

        login.setOnClickListener(view -> {
            String nombreString = nombre.getText().toString().trim();
            String emailString = email.getText().toString().trim();
            String passwordString = password.getText().toString().trim();
            String passwordValidationString = passwordValidation.getText().toString().trim();

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

            Usuario existeUsuario = accesoDatos.ConsultarUsuario(usuario.getNombre());

            if (existeUsuario != null && !existeUsuario.getMail().equals(emailString)) {
                Mensaje("¡Error en la actualización!", "No se puede cambiar el nombre porque ya está en uso por otro usuario.");
                return;
            }

            boolean actualizacionExitosa = accesoDatos.ActualizarDatosUsuario(usuario);

            if (actualizacionExitosa) {
                Mensaje("¡Actualización exitosa!", "Los datos han sido actualizados correctamente.");
            } else {
                Mensaje("¡Error en la actualización!", "No se pudieron actualizar los datos. Inténtelo nuevamente.");
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    private void Mensaje(String titulo, String mensaje) {
        Toast.makeText(this, titulo + ": " + mensaje, Toast.LENGTH_LONG).show();
    }
}
