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
import com.example.app2.model.PartidoModel;

/**
 * Actividad que muestra la información detallada de un partido seleccionado.
 *
 * Funcionalidades principales:
 * - Muestra el resultado, imagen, descripción y goleadores del partido recibido por intent.
 * - Ajusta los insets para la barra de estado y navegación.
 * - Permite cerrar la pantalla con un botón "Volver".
 *
 * Uso típico:
 * - Se lanza desde una lista de partidos, pasando un objeto PartidoModel por intent.
 * - Recupera el modelo y muestra sus datos en la interfaz.
 */
public class PartidoInfoActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    TextView equiposResultado, descripcionPartido, goleadoresPartido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidos_info);

        // Ajusta los insets para la barra de estado y navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtiene el partido seleccionado desde el intent
        PartidoModel partido = (PartidoModel) getIntent().getSerializableExtra("partido");

        // Inicializa las vistas
        equiposResultado = findViewById(R.id.equiposResultado);
        imageView = findViewById(R.id.imagenPartido);
        descripcionPartido = findViewById(R.id.descripcionPartido);
        goleadoresPartido = findViewById(R.id.goleadoresPartido);
        button = findViewById(R.id.buttonVolver);

        // Muestra los datos del partido
        equiposResultado.setText(partido.getResultado());
        imageView.setImageResource(partido.getImagen());
        descripcionPartido.setText(partido.getDescripcion());
        goleadoresPartido.setText(partido.getGoleadores());

        // Botón para cerrar la actividad
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
