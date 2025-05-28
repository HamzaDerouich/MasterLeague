package com.example.app2.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app2.R;
import com.example.app2.data.UsuarioRepository;
import com.example.app2.model.Usuario;

public class SignUpActivity extends AppCompatActivity {

    UsuarioRepository accesoDatos;
    EditText nombre, email, password , passwordValidation;
    Button login , signup ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String nombreString = nombre.getText().toString().trim();
                String emailString = email.getText().toString().trim();
                String passwordString = password.getText().toString().trim();
                String passwordValidationString = passwordValidation.getText().toString().trim();

                if (nombreString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty() || passwordValidationString.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Rellene todos los campos para continuar.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                    Toast.makeText(SignUpActivity.this, "Ingrese un email válido.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwordString.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!passwordString.equals(passwordValidationString)) {
                    Mensaje("Error en el registro", "Las contraseñas no coinciden. Por favor, intente nuevamente.");
                    return;
                }

                Usuario usuario = new Usuario(nombreString, emailString, passwordString);
                Usuario existeUsuario = accesoDatos.ConsultarUsuario(usuario.getNombre());

                if (existeUsuario != null) {
                    Mensaje("¡Registro erróneo!", "El usuario con el nombre " + nombreString + " ya existe. Por favor, elija otro nombre.");
                    return;
                }

                boolean registroExitoso = accesoDatos.RegistrarUsuario(usuario);

                if (registroExitoso) {
                    Mensaje("¡Registro exitoso!", "Usuario registrado con el nombre " + nombreString + ". ¡Bienvenido! Hemos enviado un correo de verificación a tu dirección de email.");
                } else {
                    Mensaje("¡Registro erróneo!", "No se pudo registrar al usuario. Inténtelo nuevamente.");
                }
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
    public void Mensaje(String titulo, String mensaje)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(titulo);
        dialog.setMessage(mensaje);
        dialog.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
        dialog.show();
    }
}