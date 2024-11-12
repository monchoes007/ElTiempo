package com.example.eltiempo.localidades;

import android.content.Intent;
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
import com.example.eltiempo.provincias.Provincia;
import com.example.eltiempo.provincias.ProvinciasAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Actividad para mostrar la lista de localidades de una provincia en un RecyclerView
public class Localidades extends AppCompatActivity {

    private RecyclerView rvLocalidades; // RecyclerView para mostrar las localidades
    List<Localidad> localidades = new ArrayList<>(); // Lista que almacenará las localidades obtenidas
    String provincia; // ID de la provincia, que se recibirá de la actividad anterior

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Activa el modo de pantalla completa
        setContentView(R.layout.activity_localidades); // Asigna el layout de la actividad

        // Obtiene el intent y extrae el ID de la provincia pasada desde la actividad anterior
        Intent intent = getIntent();
        provincia = intent.getStringExtra("provincia");

        // Inicializa el RecyclerView y establece un LayoutManager vertical
        rvLocalidades = findViewById(R.id.rvLocalidades);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvLocalidades.setLayoutManager(layoutManager);

        // URL para la API que obtiene los municipios de la provincia especificada
        String url = "https://www.el-tiempo.net/api/json/v2/provincias/" + provincia + "/municipios";
        Log.i("Moncho", url); // Imprime la URL en los logs para depuración

        // Cola de peticiones de Volley
        RequestQueue cola = Volley.newRequestQueue(this);

        // Configuración de la petición JSON para obtener las localidades
        JsonObjectRequest peticion = new JsonObjectRequest(
                Request.Method.GET, // Método GET para obtener datos
                url, // URL de la petición
                null, // Sin parámetros adicionales en el cuerpo de la petición
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Moncho", "" + response); // Log para depuración de la respuesta
                        JSONArray jsaLocalidades = null; // Array para almacenar los municipios obtenidos

                        try {
                            // Obtiene el array de municipios de la respuesta JSON
                            jsaLocalidades = response.getJSONArray("municipios");
                            Log.i("Moncho", "" + jsaLocalidades); // Log para depuración

                            // Itera a través del array de municipios
                            for (int i = 0; i < jsaLocalidades.length(); i++) {
                                try {
                                    // Obtiene cada objeto de localidad en la posición 'i' y crea una nueva instancia de Localidad
                                    JSONObject jsoLocalidad = jsaLocalidades.getJSONObject(i);
                                    localidades.add(
                                            new Localidad(
                                                    jsoLocalidad.getString("CODIGOINE").substring(0, 5), // Código de la localidad
                                                    jsoLocalidad.getString("NOMBRE") // Nombre de la localidad
                                            )
                                    );
                                } catch (JSONException e) {
                                    throw new RuntimeException(e); // Gestiona errores de JSON
                                }
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e); // Gestiona errores de JSON
                        }

                        // Configura el adaptador para el RecyclerView con la lista de localidades obtenida
                        LocalidadesAdapter localidadesAdapter = new LocalidadesAdapter(localidades);
                        rvLocalidades.setAdapter(localidadesAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo de errores en caso de que la petición falle
                    }
                }
        );
        // Añade la petición a la cola para que se ejecute
        cola.add(peticion);
    }
}

/*
Explicación de las Partes del Código

    - Localidades: Actividad que gestiona el despliegue de localidades en una lista interactiva (RecyclerView).
    - onCreate: Inicializa la actividad, extrae el ID de la provincia y configura el RecyclerView.
    - JsonObjectRequest: Realiza una petición HTTP para obtener las localidades de la provincia.
    - Respuesta de la petición: Si la petición es exitosa, convierte los datos en objetos Localidad y los añade a la lista localidades.
    - LocalidadesAdapter: Configura el adaptador de RecyclerView con la lista de localidades.
*/