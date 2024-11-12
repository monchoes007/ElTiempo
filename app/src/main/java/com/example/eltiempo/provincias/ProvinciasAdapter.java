package com.example.eltiempo.provincias;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eltiempo.R;
import com.example.eltiempo.localidades.Localidades;

import java.util.List;

// Adapter para manejar una lista de provincias en un RecyclerView
public class ProvinciasAdapter extends RecyclerView.Adapter<ProvinciasAdapter.ViewHolder> {

    // Lista que contiene los objetos Provincia a mostrar en el RecyclerView
    private List<Provincia> listaProvincias;

    // Constructor que recibe una lista de provincias y la asigna a la variable listaProvincias
    public ProvinciasAdapter(List<Provincia> listaProvincias) {
        this.listaProvincias = listaProvincias;
    }

    // Crea y devuelve una nueva instancia del ViewHolder para cada elemento del RecyclerView
    @NonNull
    @Override
    public ProvinciasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el diseño del elemento (provincia_item) y lo pasa al ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.provincia_item, parent, false);
        return new ProvinciasAdapter.ViewHolder(view);
    }

    // Enlaza cada elemento de la lista con el ViewHolder correspondiente
    @Override
    public void onBindViewHolder(@NonNull ProvinciasAdapter.ViewHolder holder, int position) {
        // Obtiene el objeto Provincia correspondiente a la posición actual
        Provincia provincia = listaProvincias.get(position);

        // Establece el nombre de la provincia en el TextView del ViewHolder
        holder.txtProvinciaItem.setText(provincia.getNombre());

        // Agrega un listener al TextView que reacciona al hacer clic en el nombre de la provincia
        holder.txtProvinciaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para iniciar la actividad Localidades y envía el ID de la provincia
                Intent intent = new Intent(v.getContext(), Localidades.class);
                intent.putExtra("provincia", provincia.getId());

                // Inicia la actividad Localidades con el contexto de la vista
                v.getContext().startActivity(intent);
            }
        });
    }

    // Devuelve el número de elementos en la lista de provincias
    @Override
    public int getItemCount() {
        return listaProvincias.size();
    }

    // Clase interna ViewHolder que representa cada elemento de la lista
    public class ViewHolder extends RecyclerView.ViewHolder {
        // TextView para mostrar el nombre de la provincia
        TextView txtProvinciaItem;

        // Constructor del ViewHolder que inicializa el TextView
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProvinciaItem = itemView.findViewById(R.id.txtProvinciaItem);
        }
    }
}

/*
Explicación de las Partes del Código

    - Constructor del Adapter: Recibe la lista de provincias para llenar el RecyclerView.
    - onCreateViewHolder: Infla el diseño de cada ítem (provincia_item.xml) y lo pasa al ViewHolder.
    - onBindViewHolder: Establece los datos de cada provincia en el TextView y configura el OnClickListener para abrir la actividad Localidades.
    - getItemCount: Retorna el número de elementos en la lista de provincias.
    - Clase interna ViewHolder: Contiene referencias a los elementos de cada ítem (en este caso, un solo TextView) para mejorar la eficiencia en la visualización del RecyclerView.
 */
