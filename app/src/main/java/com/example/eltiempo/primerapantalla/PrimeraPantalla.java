package com.example.eltiempo.primerapantalla;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eltiempo.R;
import com.example.eltiempo.bbdd.DatabaseHelper;
import com.example.eltiempo.provincias.Provincias;

import java.util.List;

// Clase que representa la actividad principal de la aplicación, donde se muestran los favoritos y se accede a la búsqueda de provincias
public class PrimeraPantalla extends AppCompatActivity {

    // Botón para iniciar la búsqueda de provincias y RecyclerView para mostrar los favoritos
    Button btnBuscar;
    RecyclerView rvFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Activa el modo EdgeToEdge para una mejor integración visual
        setContentView(R.layout.activity_primera_pantalla);

        // Configura los márgenes de los elementos según las barras de sistema (margen superior e inferior)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Enlaza los elementos de la interfaz
        btnBuscar = findViewById(R.id.btnBuscar);
        rvFavoritos = findViewById(R.id.rvFavoritos);

        // Configura el layout del RecyclerView en un GridLayout de 2 columnas
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvFavoritos.setLayoutManager(gridLayoutManager);

        // Asigna un listener al botón para lanzar la actividad Provincias al hacer clic
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Provincias.class);
                startActivity(intent); // Inicia la actividad de Provincias
            }
        });

        // Carga los favoritos en el RecyclerView
        rellenaFavoritos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarga los favoritos cada vez que la actividad vuelva a estar en primer plano
        rellenaFavoritos();
    }

    // Método para obtener la lista de favoritos desde la base de datos
    private List<String[]> cargaListaFavoritos() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        return databaseHelper.devuelveFavoritos(); // Devuelve la lista de favoritos almacenados
    }

    // Método que crea un adaptador con la lista de favoritos y lo asigna al RecyclerView
    private void rellenaFavoritos() {
        PrimeraPantallaAdapter primeraPantallaAdapter = new PrimeraPantallaAdapter(cargaListaFavoritos());
        rvFavoritos.setAdapter(primeraPantallaAdapter); // Asigna el adaptador al RecyclerView
    }
}

/*
Explicación de las Partes del Código

    - Clase PrimeraPantalla: Muestra la actividad principal, que incluye un botón para buscar provincias y un RecyclerView que lista los favoritos guardados.
    - Método onCreate: Configura el layout y el diseño de la interfaz, activando el botón para navegar a la pantalla de provincias y mostrando la lista de favoritos.
    - Método onResume: Recarga la lista de favoritos cada vez que la actividad vuelve a primer plano.
    - Método cargaListaFavoritos: Obtiene la lista de favoritos desde la base de datos utilizando DatabaseHelper.
    - Método rellenaFavoritos: Crea un adaptador de la lista de favoritos y lo asigna al RecyclerView para que se muestre en la interfaz.
*/