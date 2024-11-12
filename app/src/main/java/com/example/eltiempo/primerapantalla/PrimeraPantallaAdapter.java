package com.example.eltiempo.primerapantalla;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

// Adaptador para un RecyclerView que muestra una lista de favoritos en la primera pantalla
public class PrimeraPantallaAdapter extends RecyclerView.Adapter<PrimeraPantallaAdapter.ViewHolder> {

    // Lista que almacena los datos de los favoritos como arrays de String
    private List<String[]> listaFavoritos;

    // Constructor que recibe y asigna la lista de favoritos
    public PrimeraPantallaAdapter(List<String[]> listaFavoritos) {
        this.listaFavoritos = listaFavoritos;
    }

    // Infla el diseño del elemento de la lista y crea el ViewHolder
    @NonNull
    @Override
    public PrimeraPantallaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla la vista desde el layout favorito_item.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorito_item, parent, false);

        // Log para verificar el ViewGroup en el que se está inflando el layout
        Log.i("Moncho", ">" + parent);

        // Retorna una nueva instancia de ViewHolder que contiene la vista
        return new PrimeraPantallaAdapter.ViewHolder(view);
    }

    // Método que vincula los datos de cada elemento de la lista al ViewHolder
    @Override
    public void onBindViewHolder(@NonNull PrimeraPantallaAdapter.ViewHolder holder, int position) {
        // Obtiene el favorito actual de la lista
        String[] favorito = listaFavoritos.get(position);

        // Asigna el nombre del favorito al TextView correspondiente en el ViewHolder
        holder.txtFavoritoNombre.setText(favorito[0]);

        // Construye la URL para la petición de datos del tiempo del favorito actual
        String url = "https://www.el-tiempo.net/api/json/v2/provincias/" + favorito[1] + "/municipios/" + favorito[2];

        // Crea una cola de peticiones con Volley para realizar la petición a la API
        RequestQueue cola = Volley.newRequestQueue(holder.itemView.getContext());

        // Configura la petición JSON para obtener datos del tiempo actual
        JsonObjectRequest peticion = new JsonObjectRequest(
                Request.Method.GET, // Método de la petición: GET
                url, // URL de la API
                null, // Parámetros adicionales (no se envían datos)

                // Listener que maneja la respuesta de la API
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtiene la temperatura actual del JSON de respuesta y la asigna al TextView
                            holder.txtFavoritoTemperatura.setText(response.getString("temperatura_actual") + "º");

                            // Configura el botón de eliminar favorito con un listener
                            holder.ibtnFavoritoEliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Crea una instancia de DatabaseHelper y elimina el favorito de la base de datos
                                    DatabaseHelper databaseHelper = new DatabaseHelper(v.getContext());
                                    databaseHelper.eliminarFavorito(favorito[2]);

                                    // Remueve el elemento de la lista y notifica al adaptador para actualizar la vista
                                    listaFavoritos.remove(position);
                                    notifyItemRemoved(position);
                                }
                            });

                            // Construye la URL de la imagen y la carga en el ImageView utilizando la clase Herramientas
                            String url = "https://www.aemet.es/imagenes/png/estado_cielo/" + response.getJSONObject("stateSky").getString("id") + "_g.png";
                            Herramientas.cargaImagen(holder.imgFavoritoImagen, url);

                        } catch (JSONException e) {
                            // Gestiona excepciones de JSON
                            throw new RuntimeException(e);
                        }
                    }
                },

                // Listener para manejar posibles errores en la petición
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo de errores en la petición (vacío en este caso)
                    }
                }
        );

        // Añade la petición a la cola de peticiones de Volley para que se ejecute
        cola.add(peticion);
    }

    // Devuelve el tamaño de la lista de favoritos
    @Override
    public int getItemCount() {
        return listaFavoritos.size();
    }

    // Clase interna ViewHolder que representa cada elemento de la lista
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Elementos de la interfaz para mostrar datos del favorito
        TextView txtFavoritoNombre;
        TextView txtFavoritoTemperatura;
        ImageView imgFavoritoImagen;
        ImageButton ibtnFavoritoEliminar;

        // Constructor del ViewHolder que inicializa los elementos de la interfaz
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFavoritoNombre = itemView.findViewById(R.id.txtFavoritoNombre);
            txtFavoritoTemperatura = itemView.findViewById(R.id.txtFavoritoTemperatura);
            imgFavoritoImagen = itemView.findViewById(R.id.imgFavoritoImagen);
            ibtnFavoritoEliminar = itemView.findViewById(R.id.ibtnFavoritoEliminar);
        }
    }
}

/*
Explicación de las Partes del Código

    - Adaptador PrimeraPantallaAdapter: Muestra una lista de favoritos en un RecyclerView.
    - Método onCreateViewHolder: Infla la vista de cada elemento de la lista desde el layout favorito_item.xml.
    - Método onBindViewHolder: Vincula los datos de cada favorito al ViewHolder. Realiza una petición a la API para obtener y mostrar la temperatura y el ícono del estado del cielo. También gestiona la eliminación de favoritos.
    - Clase interna ViewHolder: Define los elementos visuales que forman cada ítem del RecyclerView, permitiendo un acceso rápido para la asignación de datos.
*/
