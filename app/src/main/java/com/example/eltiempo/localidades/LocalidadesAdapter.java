package com.example.eltiempo.localidades;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eltiempo.R;
import com.example.eltiempo.ciudad.Ciudad;

import java.util.List;

// Adaptador para el RecyclerView que muestra la lista de localidades
public class LocalidadesAdapter extends RecyclerView.Adapter<LocalidadesAdapter.ViewHolder> {

    // Lista de objetos Localidad que representa las localidades a mostrar
    private List<Localidad> listaLocalidades;

    // Constructor que recibe la lista de localidades y la asigna a la variable del adaptador
    public LocalidadesAdapter(List<Localidad> listaLocalidades) {
        this.listaLocalidades = listaLocalidades;
    }

    @NonNull
    @Override
    public LocalidadesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout para cada elemento de localidad y crea una nueva instancia de ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.localidad_item, parent, false);
        return new LocalidadesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalidadesAdapter.ViewHolder holder, int position) {
        // Obtiene la localidad en la posición actual de la lista
        Localidad localidad = listaLocalidades.get(position);
        // Configura el nombre de la localidad en el TextView del ViewHolder
        holder.txtLocalidadItem.setText(localidad.getNombre());

        // Configura un listener para el elemento, que inicia la actividad Ciudad al hacer clic
        holder.txtLocalidadItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Ciudad.class);
                intent.putExtra("localidad", localidad.getId()); // Pasa el ID de la localidad a la siguiente actividad
                v.getContext().startActivity(intent); // Inicia la actividad Ciudad
            }
        });
    }

    @Override
    public int getItemCount() {
        // Devuelve el número total de localidades en la lista
        return listaLocalidades.size();
    }

    // ViewHolder interno que representa cada elemento visual del RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        // TextView que muestra el nombre de la localidad
        TextView txtLocalidadItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Enlaza el TextView con su ID en el layout localidad_item
            txtLocalidadItem = itemView.findViewById(R.id.txtLocalidadItem);
        }
    }
}

/*
Explicación de las Partes del Código

    - LocalidadesAdapter: Adaptador de RecyclerView que gestiona la lista de localidades.
    - onCreateViewHolder: Infla el layout localidad_item para cada elemento de la lista y lo asigna al ViewHolder.
    - onBindViewHolder: Establece el texto del TextView con el nombre de cada localidad y configura un listener para lanzar la actividad Ciudad cuando se haga clic en el elemento.
    - getItemCount: Devuelve la cantidad de elementos en listaLocalidades, necesaria para que el adaptador sepa cuántos elementos debe mostrar.
    - Clase ViewHolder: Enlaza el elemento visual de cada localidad (txtLocalidadItem) para ser usado en el RecyclerView.
*/
