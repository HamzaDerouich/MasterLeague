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

import com.example.app2.R;
import com.example.app2.model.Goleador;

public class GoleadoresInfoActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    TextView equiposResultado, descripcionPartido, goleadoresPartido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goleadores_info);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Goleador goleador = (Goleador) getIntent().getSerializableExtra("goleador");

        equiposResultado = findViewById(R.id.equiposResultado);
        imageView = findViewById(R.id.imagenPartido);
        descripcionPartido = findViewById(R.id.descripcionPartido);
        goleadoresPartido = findViewById(R.id.goleadoresPartido);
        button = findViewById(R.id.buttonVolver);

        equiposResultado.setText(goleador.getNombre());
        imageView.setImageResource(goleador.getImagen());
        descripcionPartido.setText(goleador.getDescripcion());
        goleadoresPartido.setText(goleador.getGoles());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
