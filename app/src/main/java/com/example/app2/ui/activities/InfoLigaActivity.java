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

import com.bumptech.glide.Glide;
import com.example.app2.R;
import com.example.app2.model.LigaModel;

/**
 * Actividad que muestra la información detallada de una liga seleccionada.
 *
 * Funcionalidades principales:
 * - Muestra el nombre, logo, país, año de fundación y descripción de la liga.
 * - Ajusta los insets para la barra de estado y navegación.
 * - Permite cerrar la pantalla con un botón.
 * - Utiliza Glide para cargar la imagen del logo de la liga.
 *
 * Uso típico:
 * - Se lanza desde otra actividad pasando un objeto LigaModel por intent.
 * - Recupera el modelo y muestra sus datos en la interfaz.
 */
public class InfoLigaActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private TextView nombre, descripcion, año, pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_liga);

        // Ajusta los insets para la barra de estado y navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtiene la liga seleccionada desde el intent
        LigaModel liga = (LigaModel) getIntent().getSerializableExtra("liga");

        // Inicializa las vistas
        nombre = findViewById(R.id.NombreLiga);
        imageView = findViewById(R.id.imagenLiga);
        descripcion = findViewById(R.id.descripcionLiga);
        pais = findViewById(R.id.paisLiga);
        año = findViewById(R.id.añoFundada);
        button = findViewById(R.id.button);

        // Muestra los datos de la liga si están disponibles
        if (liga != null) {
            nombre.setText(liga.getName());

            // Cargar imagen con Glide
            Glide.with(this)
                    .load(liga.getLogo())
                    .placeholder(R.drawable.ic_launcher_foreground) // Imagen por defecto
                    .error(R.drawable.ic_launcher_foreground) // Imagen si hay error
                    .into(imageView);

            // Puedes agregar aquí la lógica para mostrar país, año y descripción si están en LigaModel
            // pais.setText(liga.getCountry().getName());
            // año.setText(String.valueOf(liga.getFounded()));
            // descripcion.setText(liga.getDescription());
        }

        // Botón para cerrar la actividad
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}