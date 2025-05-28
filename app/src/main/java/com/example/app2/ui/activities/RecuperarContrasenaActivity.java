package com.example.app2.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app2.R;
import com.example.app2.data.UsuarioRepository;
import com.example.app2.model.UsuarioModel;

import java.util.Random;

/**
 * Actividad para recuperar la contraseña del usuario mediante verificación por correo electrónico.
 *
 * Funcionalidades principales:
 * - Permite al usuario ingresar su correo electrónico registrado.
 * - Verifica si el correo existe en la base de datos local.
 * - Si el correo está registrado, genera un código de verificación aleatorio.
 * - Envía el código de verificación al correo del usuario usando un cliente de correo externo.
 * - Navega a la pantalla de verificación de código (CodigoVerficacionActivity) pasando el código generado.
 * - Muestra mensajes de error si el campo está vacío o el correo no está registrado.
 *
 * Uso típico:
 * - El usuario accede a esta pantalla desde la opción "¿Olvidaste tu contraseña?" en el login.
 * - Ingresa su correo, recibe el código y continúa el proceso de recuperación.
 */
public class RecuperarContrasenaActivity extends AppCompatActivity {

    EditText correo;
    Button buttonSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_contrasena);

        // Ajusta los insets para la barra de estado y navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        correo = findViewById(R.id.editTextEmail);
        buttonSiguiente = findViewById(R.id.button);

        buttonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correoString = correo.getText().toString().trim();

                if (correoString.isEmpty()) {
                    Toast.makeText(RecuperarContrasenaActivity.this, "Ingrese su correo.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Buscar usuario por correo
                UsuarioRepository repo = new UsuarioRepository(RecuperarContrasenaActivity.this);
                UsuarioModel usuario = repo.consultarUsuarioPorEmail(correoString);

                if (usuario == null) {
                    Toast.makeText(RecuperarContrasenaActivity.this, "Correo no registrado.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Genera código de verificación y lo envía por correo
                int numero_verificacion = GenerarAleatorio();
                EnviarCorreo(correoString, numero_verificacion);

                // Navega a la pantalla de verificación de código
                Intent intent = new Intent(RecuperarContrasenaActivity.this, CodigoVerficacionActivity.class);
                intent.putExtra("num_verificacion", numero_verificacion);
                startActivity(intent);

                Toast.makeText(RecuperarContrasenaActivity.this, "Abriendo cliente de correo...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Genera un número aleatorio de 6 dígitos para usar como código de verificación.
     * @return Código de verificación.
     */
    private int GenerarAleatorio() {
        return new Random().nextInt(900000) + 100000;
    }

    /**
     * Envía un correo electrónico con el código de verificación al usuario.
     * @param destinatario Correo electrónico del usuario.
     * @param numero Código de verificación generado.
     */
    public void EnviarCorreo(String destinatario, int numero) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{destinatario});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Recuperación de contraseña - MasterLeague");
        intent.putExtra(Intent.EXTRA_TEXT, "Tu código de verificación es: " + numero);
        try {
            startActivity(Intent.createChooser(intent, "Enviar correo..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No hay clientes de correo instalados.", Toast.LENGTH_SHORT).show();
        }
    }
}