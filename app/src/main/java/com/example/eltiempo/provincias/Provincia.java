package com.example.eltiempo.provincias;

// Clase Provincia que representa el modelo de datos de una provincia
public class Provincia {

    // Atributo id que almacena el identificador único de la provincia
    private String id;

    // Atributo nombre que almacena el nombre de la provincia
    private String nombre;

    // Constructor que inicializa los atributos id y nombre
    public Provincia(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getter para obtener el valor del atributo id
    public String getId() {
        return id;
    }

    // Setter para establecer un valor en el atributo id
    public void setId(String id) {
        this.id = id;
    }

    // Getter para obtener el valor del atributo nombre
    public String getNombre() {
        return nombre;
    }

    // Setter para establecer un valor en el atributo nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

/*
Explicación de las Partes del Código

    - Atributos id y nombre: Representan el identificador único y el nombre de cada provincia.
    - Constructor: Inicializa los atributos id y nombre con valores proporcionados.
    - Métodos Getter y Setter: Proporcionan acceso controlado a los atributos de la clase, permitiendo obtener o modificar los valores de id y nombre.
*/
