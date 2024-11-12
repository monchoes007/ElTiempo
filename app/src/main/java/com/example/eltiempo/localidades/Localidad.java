package com.example.eltiempo.localidades;

// Clase que representa una localidad con un identificador único y un nombre
public class Localidad {
    private String id;       // Identificador de la localidad (por ejemplo, código INE)
    private String nombre;    // Nombre de la localidad

    // Constructor que inicializa el ID y el nombre de la localidad
    public Localidad(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Método getter para obtener el ID de la localidad
    public String getId() {
        return id;
    }

    // Método setter para establecer el ID de la localidad
    public void setId(String id) {
        this.id = id;
    }

    // Método getter para obtener el nombre de la localidad
    public String getNombre() {
        return nombre;
    }

    // Método setter para establecer el nombre de la localidad
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

/*
Explicación de las Partes del Código

    - Localidad: Define una clase de objeto que representa una localidad con un id único y un nombre.
    - Constructor: Permite inicializar una nueva instancia de Localidad con los parámetros id y nombre.
    - Getters y Setters: Permiten el acceso y la modificación de los atributos id y nombre desde otras clases, manteniendo la encapsulación.
*/
