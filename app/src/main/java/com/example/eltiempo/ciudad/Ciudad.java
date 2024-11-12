package com.example.eltiempo.ciudad;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eltiempo.R;
import com.example.eltiempo.bbdd.DatabaseHelper;
import com.example.eltiempo.herramientas.Herramientas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Clase Ciudad que extiende AppCompatActivity, representando la vista de la ciudad
 */
public class Ciudad extends AppCompatActivity {

    // Declaración de variables para los elementos de la interfaz de usuario
    TextView txtCiudadNombre;
    TextView txtCiudadTemperatura;
    TextView txtCiudadHumedad;
    TextView txtCiudadCielo;
    ImageView imgCiudadImagenTiempo;
    FloatingActionButton btnFavorito;

    // Variables para almacenar el código de la provincia y localidad
    String provincia;
    String localidad;

    // Método que se ejecuta al crear la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa el modo EdgeToEdge para una experiencia de pantalla completa
        EdgeToEdge.enable(this);

        // Configura el diseño de la actividad
        setContentView(R.layout.activity_ciudad);

        // Configura el listener para los insets del sistema, ajustando el padding en las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtiene el Intent que inició la actividad y extrae los datos de la localidad
        Intent intent = getIntent();
        provincia = intent.getStringExtra("localidad").substring(0, 2); // Los primeros dos caracteres de la localidad representan la provincia
        localidad = intent.getStringExtra("localidad");

        // Asocia las variables a los elementos de la interfaz en el diseño
        txtCiudadNombre = findViewById(R.id.txtCiudadNombre);
        txtCiudadTemperatura = findViewById(R.id.txtCiudadTemperatura);
        txtCiudadHumedad = findViewById(R.id.txtCiudadHumedad);
        txtCiudadCielo = findViewById(R.id.txtCiudadCielo);
        imgCiudadImagenTiempo = findViewById(R.id.imgCiudadImagenTiempo);
        btnFavorito = findViewById(R.id.btnFavorito);

        // Configura el botón de favorito para alternar el estado entre favorito y no favorito
        btnFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instancia de la base de datos para gestionar favoritos
                DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());

                // Verifica si el botón ya tiene el color amarillo, indicando que es un favorito
                if(btnFavorito.getImageTintList().getDefaultColor() == Color.YELLOW) {
                    // Si ya es favorito, lo elimina de la base de datos y cambia el color a negro
                    databaseHelper.eliminarFavorito(localidad);
                    btnFavorito.setImageTintList(ColorStateList.valueOf(Color.BLACK));
                } else {
                    // Si no es favorito, lo añade a la base de datos y cambia el color a amarillo
                    databaseHelper.insertarFavorito(txtCiudadNombre.getText().toString(), provincia, localidad);
                    btnFavorito.setImageTintList(ColorStateList.valueOf(Color.YELLOW));
                }
            }
        });

        // Llama al método para cargar los datos del municipio
        cargaDatosMunicipio(provincia, localidad);
    }

    /**
     * Método para cargar datos de una API externa usando la biblioteca Volley
     * @param provincia String con el código de la provincia que nos interesa
     * @param localidad String con el código de la localidad que nos interesa
     */
    private void cargaDatosMunicipio(String provincia, String localidad) {
        // Construye la URL de la API para obtener la información del clima del municipio
        String url = "https://www.el-tiempo.net/api/json/v2/provincias/" + provincia + "/municipios/" + localidad;

        // Crea una cola de peticiones de Volley
        RequestQueue cola = Volley.newRequestQueue(this);

        // Configura una petición JSON a la URL especificada
        JsonObjectRequest peticion = new JsonObjectRequest(
                Request.Method.GET, // Método de la petición
                url,                // URL de la API
                null,               // No hay datos adicionales en la petición
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtiene datos del JSON y los asigna a los elementos de la interfaz
                            txtCiudadNombre.setText(response.getJSONObject("municipio").getString("NOMBRE"));
                            txtCiudadCielo.setText(response.getJSONObject("stateSky").getString("description"));
                            txtCiudadHumedad.setText(response.getString("humedad") + "%");
                            txtCiudadTemperatura.setText(response.getString("temperatura_actual") + "º");

                            // Comprueba en la base de datos si el municipio es un favorito
                            DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
                            if (databaseHelper.buscarFavorito(localidad)) {
                                // Si es favorito, cambia el color del botón a amarillo
                                btnFavorito.setImageTintList(ColorStateList.valueOf(Color.YELLOW));
                            }

                            String url = "https://www.aemet.es/imagenes/png/estado_cielo/" + response.getJSONObject("stateSky").getString("id") + "_g.png";
                            Herramientas.cargaImagen(imgCiudadImagenTiempo,url);

                        } catch (JSONException e) {
                            // Gestiona excepciones de JSON
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gestiona errores de la petición JSON (vacío en este caso)
                    }
                }
        );
        // Añade la petición JSON a la cola
        cola.add(peticion);
    }
}

/*
Resumen
    - Este código realiza las siguientes acciones:
        - Configuración y Ajustes: Define el diseño de la actividad en pantalla completa.
        - Obtención de Datos del Intent: Recibe los datos de provincia y localidad.
        - Interacción con el Botón Favorito: Agrega o elimina el municipio de la lista de favoritos y cambia su color de tinte entre negro y amarillo.
        - Carga de Datos: Hace una solicitud a la API del clima y muestra los datos de temperatura, humedad y estado del cielo.
        - Carga de Imagen del Estado del Cielo: Realiza una solicitud de imagen que representa el estado del clima y la muestra en un ImageView.
*/
