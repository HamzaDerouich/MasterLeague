package com.example.app2.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app2.R;
import com.example.app2.model.GoleadorModel;

/**
 * Actividad que muestra la información detallada de un goleador seleccionado.
 *
 * Funcionalidades principales:
 * - Muestra los datos del goleador recibidos por intent (nombre, foto, equipo, goles, asistencias, etc.).
 * - Ajusta los insets para la barra de estado y navegación.
 * - Permite mostrar la información en el layout activity_goleadores_info.
 *
 * Uso típico:
 * - Se lanza desde una lista de goleadores, pasando un objeto GoleadorModel por intent.
 * - Recupera el modelo y muestra sus datos en la interfaz.
 */
public class GoleadoresInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goleadores_info);

        // Ajusta los insets para la barra de estado y navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
