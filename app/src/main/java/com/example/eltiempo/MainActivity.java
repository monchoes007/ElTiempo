package com.example.eltiempo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eltiempo.primerapantalla.PrimeraPantalla;

// Clase MainActivity que extiende AppCompatActivity, representando la pantalla principal de la aplicación
public class MainActivity extends AppCompatActivity {

    // Declaración de un ImageView llamado imgBotonInicial, que actuará como botón de inicio
    ImageView imgBotonInicial;

    // Método que se ejecuta al crear la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa el modo EdgeToEdge para una experiencia de pantalla completa
        EdgeToEdge.enable(this);

        // Establece el diseño de la actividad principal desde el archivo activity_main.xml
        setContentView(R.layout.activity_main);

        // Configura el listener para los insets del sistema, ajustando el padding en las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Obtiene los márgenes del sistema y ajusta el padding del contenedor principal
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Asocia imgBotonInicial con el ImageView definido en el diseño
        imgBotonInicial = findViewById(R.id.imgBotonInicial);

        // Establece un listener de clic para imgBotonInicial
        imgBotonInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic, se crea un Intent para navegar a la actividad PrimeraPantalla
                Intent intent = new Intent(getBaseContext(), PrimeraPantalla.class);

                // Inicia la actividad PrimeraPantalla
                startActivity(intent);
            }
        });
    }
}

/*
Explicación de las Partes del Código

    - Edge-to-Edge: Configura la actividad para que ocupe toda la pantalla, incluyendo los márgenes del sistema.
    - Window Insets Listener: Ajusta el padding de los márgenes para que el contenido de la interfaz respete las áreas seguras del sistema.
    - Click Listener en imgBotonInicial: Configura un ImageView como botón que al hacer clic inicia PrimeraPantalla.

 */
