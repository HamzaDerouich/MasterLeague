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

/**
 * Actividad para la verificación del código enviado por correo en el proceso de recuperación de contraseña.
 *
 * Funcionalidades principales:
 * - Recibe el código de verificación generado y enviado al correo del usuario.
 * - Permite al usuario ingresar el código recibido.
 * - Valida que el código ingresado coincida con el enviado.
 * - Si el código es correcto, permite avanzar a la pantalla de restablecimiento de contraseña.
 * - Muestra mensajes de error si el campo está vacío o el código es incorrecto.
 */
public class CodigoVerficacionActivity extends AppCompatActivity {

    int numeroVerficacionCorreo;
    EditText numeroEditText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_completar_codigo_verificacion);

        // Ajusta los insets para la barra de estado y navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtiene el código de verificación enviado por correo desde el intent
        Bundle extras = getIntent().getExtras();
        numeroVerficacionCorreo = extras.getInt("num_verificacion");

        numeroEditText = findViewById(R.id.editTextNumeroVerificacion);
        button = findViewById(R.id.button);

        // Valida el número ingresado por el usuario
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroIngresado = numeroEditText.getText().toString();

                if (numeroIngresado.isEmpty()) {
                    Toast.makeText(CodigoVerficacionActivity.this, "Por favor, ingresa el número de verificación.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int numeroInteger = Integer.parseInt(numeroIngresado);

                Toast.makeText(CodigoVerficacionActivity.this, "Número recibido: " + numeroVerficacionCorreo, Toast.LENGTH_SHORT).show();

                if (numeroVerficacionCorreo == numeroInteger) {
                    Toast.makeText(CodigoVerficacionActivity.this, "Número válido.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CodigoVerficacionActivity.this, RestablecerContrasenaActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CodigoVerficacionActivity.this, "Número no válido!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}