package com.example.app2.ui;

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
import com.example.app2.model.Liga;

public class InfoLigaActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private TextView nombre, descripcion, año, pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_liga);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Liga liga = (Liga) getIntent().getSerializableExtra("liga");

        // Initialize views
        nombre = findViewById(R.id.NombreLiga);
        imageView = findViewById(R.id.imagenLiga);
        descripcion = findViewById(R.id.descripcionLiga);
        pais = findViewById(R.id.paisLiga);
        año = findViewById(R.id.añoFundada);
        button = findViewById(R.id.button);

        if (liga != null) {
            nombre.setText(liga.getNombre());

            // Cargar imagen con Glide
            Glide.with(this)
                    .load(liga.getImagenUrl())
                    .placeholder(R.drawable.ic_launcher_foreground) // Imagen por defecto
                    .error(R.drawable.ic_launcher_foreground) // Imagen si hay error
                    .into(imageView);

            descripcion.setText(liga.getDescripcion());
            pais.setText(liga.getPais());
            año.setText(liga.getAño_fundada());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}