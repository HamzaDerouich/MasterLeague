package com.example.app2.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app2.R;

/**
 * Actividad de bienvenida/inicio para la aplicación MasterLeague.
 *
 * Funcionalidades principales:
 * - Muestra una pantalla inicial con un mensaje motivacional.
 * - Ajusta los insets para la barra de estado y navegación.
 * - Permite al usuario avanzar a la pantalla de login al pulsar el botón "comenzar".
 * - Muestra un Toast motivacional al iniciar sesión.
 *
 * Uso típico:
 * - Es la primera pantalla que ve el usuario al abrir la app.
 * - Al pulsar el botón, navega a LoginActivity.
 */
public class LoginInicioActivity extends AppCompatActivity {

    TextView comenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_inicio);

        // Ajusta los insets para la barra de estado y navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        comenzar = findViewById(R.id.button2);

        // Al pulsar el botón, navega a la pantalla de login y muestra un mensaje motivacional
        comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginInicioActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(LoginInicioActivity.this, "El balón no se rinde, ¡tú tampoco!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}