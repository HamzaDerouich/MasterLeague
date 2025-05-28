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
import com.example.app2.model.Partido;

public class PartidoInfoActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    TextView equiposResultado, descripcionPartido, goleadoresPartido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidos_info);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Partido partido = (Partido) getIntent().getSerializableExtra("partido");

        equiposResultado = findViewById(R.id.equiposResultado);
        imageView = findViewById(R.id.imagenPartido);
        descripcionPartido = findViewById(R.id.descripcionPartido);
        goleadoresPartido = findViewById(R.id.goleadoresPartido);
        button = findViewById(R.id.buttonVolver);

        equiposResultado.setText(partido.getResultado());
        imageView.setImageResource(partido.getImagen());
        descripcionPartido.setText(partido.getDescripcion());
        goleadoresPartido.setText(partido.getGoleadores());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
