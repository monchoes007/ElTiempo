package com.example.eltiempo.herramientas;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

// Clase de utilidad con métodos estáticos para operaciones comunes
public class Herramientas {

    // Método estático para cargar una imagen desde una URL en un ImageView
    public static void cargaImagen(ImageView contenedor, String url) {

        // Crea una cola de peticiones de Volley con el contexto del ImageView
        RequestQueue cola = Volley.newRequestQueue(contenedor.getContext());

        // Configura una petición de imagen usando Volley para obtener una imagen desde la URL especificada
        ImageRequest peticionImagen = new ImageRequest(
                url,    // URL de la imagen a cargar
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        // Cuando se recibe la imagen, se asigna al ImageView
                        contenedor.setImageBitmap(response);
                    }
                },
                0, 0, ImageView.ScaleType.CENTER, null,  // Tamaño y escala de la imagen (0 para tamaño original)
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo de errores en caso de fallo en la carga de la imagen (vacío en este caso)
                    }
                }
        );

        // Añade la petición de imagen a la cola de peticiones de Volley para su procesamiento
        cola.add(peticionImagen);
    }
}

/*
Explicación de las Partes del Código

    - cargaImagen: Este método permite cargar una imagen desde una URL en un ImageView, utilizando la biblioteca Volley.
    - RequestQueue: Crea una cola de peticiones para gestionar solicitudes de red, en este caso, para descargar una imagen.
    - ImageRequest: Configura una petición para descargar la imagen en formato Bitmap desde la URL. En caso de éxito, asigna la imagen al ImageView; si falla, ejecuta el onErrorResponse, que aquí no contiene acciones específicas.
    - Cola de peticiones: Añade la petición a la cola para su ejecución, administrando así las solicitudes de red de manera asíncrona.
*/