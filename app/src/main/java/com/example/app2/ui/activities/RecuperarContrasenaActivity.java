package com.example.app2.ui;

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

import java.util.Random;

public class RecuperarContrasenaActivity extends AppCompatActivity {

    EditText correo ;
    Button buttonSiguiente;
    Random numeroVerificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_contrasena);
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
                String correoString = correo.getText().toString();
                if( correoString.isEmpty() )
                {
                    Toast.makeText(RecuperarContrasenaActivity.this, "Rellene todos las campos, para seguir!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int numero_verificacion = GenerarAleatorio();
                    EnviarCorreo(numero_verificacion);
                    Intent intent = new Intent(RecuperarContrasenaActivity.this, CodigoVerficacionActivity.class);
                    intent.putExtra("num_verificacion",numero_verificacion);
                    startActivity(intent);
                    Toast.makeText(RecuperarContrasenaActivity.this, "Enviando correo.....", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private int GenerarAleatorio()
    {
        int numeroVerificacion = new Random().nextInt(900000) + 100000;
        return  numeroVerificacion;
    }


    // Método para enviar correo con número de verficación
    public static  void EnviarCorreo(int numero)
    {

    }

}