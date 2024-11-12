package com.example.eltiempo.provincias;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eltiempo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Clase Provincias que extiende AppCompatActivity, representando la pantalla donde se muestra la lista de provincias
public class Provincias extends AppCompatActivity {

    // RecyclerView que mostrará la lista de provincias
    private RecyclerView rvProvincias;

    // Lista de objetos Provincia que almacenará los datos obtenidos de la API
    List<Provincia> provincias = new ArrayList<>();

    // Método que se ejecuta al crear la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa el modo EdgeToEdge para una experiencia de pantalla completa
        EdgeToEdge.enable(this);

        // Establece el diseño de la actividad desde el archivo activity_provincias.xml
        setContentView(R.layout.activity_provincias);

        // Inicializa el RecyclerView utilizando su ID
        rvProvincias = findViewById(R.id.rvProvincias);

        // Configura el RecyclerView con un LinearLayoutManager en orientación vertical
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvProvincias.setLayoutManager(layoutManager);

        // URL de la API que proporciona los datos de las provincias
        String url = "https://www.el-tiempo.net/api/json/v2/provincias";

        // Crea una nueva cola de peticiones usando Volley
        RequestQueue cola = Volley.newRequestQueue(this);

        // Crea una petición JSON para obtener los datos de las provincias de la API
        JsonObjectRequest peticion = new JsonObjectRequest(
                Request.Method.GET, // Método de la petición: GET
                url, // URL de la API
                null, // Parámetros adicionales de la petición (null en este caso)

                // Listener que maneja la respuesta de la API
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log para verificar la respuesta recibida
                        Log.i("Moncho", "" + response);

                        JSONArray jsaProvincias = null;

                        try {
                            // Obtiene el array de provincias del JSON de respuesta
                            jsaProvincias = response.getJSONArray("provincias");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // Itera sobre cada objeto en el array JSON de provincias
                        for (int i = 0; i < jsaProvincias.length(); i++) {
                            try {
                                // Obtiene cada objeto JSON de provincia
                                JSONObject jsoProvincia = jsaProvincias.getJSONObject(i);

                                // Agrega una nueva instancia de Provincia a la lista con los datos obtenidos
                                provincias.add(new Provincia(jsoProvincia.getString("CODPROV"), jsoProvincia.getString("NOMBRE_PROVINCIA")));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        // Crea un adaptador de tipo ProvinciasAdapter con la lista de provincias
                        ProvinciasAdapter provinciasAdapter = new ProvinciasAdapter(provincias);

                        // Asigna el adaptador al RecyclerView
                        rvProvincias.setAdapter(provinciasAdapter);
                    }
                },

                // Listener que maneja posibles errores en la petición
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Aquí se pueden gestionar los posibles errores de conexión o de datos
                    }
                }
        );

        // Agrega la petición a la cola de peticiones de Volley para ejecutarla
        cola.add(peticion);
    }
}

/*
Explicación de las Partes del Código

    - RecyclerView: Se configura con un LinearLayoutManager vertical para mostrar las provincias en una lista.
    - Petición a la API con Volley: Realiza una petición GET a la API para obtener datos de las provincias.
    - Manejo de la Respuesta: La respuesta JSON de la API se procesa para extraer el array de provincias, y cada una se agrega a la lista provincias.
    - Configuración del Adaptador: Una vez cargada la lista, se crea y asigna un ProvinciasAdapter al RecyclerView para que muestre los datos.
 */