package com.example.eltiempo.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

// Clase DatabaseHelper que extiende SQLiteOpenHelper para manejar la base de datos SQLite en Android
public class DatabaseHelper extends SQLiteOpenHelper {

    // Constructor principal, recibe el contexto y llama al constructor padre con el nombre de la base de datos y la versión
    public DatabaseHelper(Context context) {
        super(context, "elTiempo.db", null, 1); // Nombre de la base de datos: "elTiempo.db" y versión 1
    }

    // Constructor adicional con parámetros opcionales para el nombre, fábrica de cursores y versión de la base de datos
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Método que se llama la primera vez que se crea la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Consulta SQL para crear una tabla llamada "favoritos" con columnas: id (clave primaria), nombre, provincia y municipio
        String createTableQuery = "CREATE TABLE favoritos (id INTEGER PRIMARY KEY, nombre TEXT,provincia TEXT,municipio TEXT)";
        db.execSQL(createTableQuery); // Ejecuta la consulta SQL
    }

    // Método para actualizar la estructura de la base de datos cuando se incrementa la versión
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En este caso, el método está vacío, lo que significa que aún no se maneja la actualización de la base de datos
    }

    // Método para insertar un nuevo registro en la tabla "favoritos"
    public void insertarFavorito(String nombre, String provincia, String municipio) {
        SQLiteDatabase db = getWritableDatabase(); // Obtiene una instancia de la base de datos en modo escritura

        // Crea un objeto ContentValues para almacenar los valores de las columnas a insertar
        ContentValues values = new ContentValues();
        values.put("nombre", nombre); // Inserta el nombre
        values.put("provincia", provincia); // Inserta la provincia
        values.put("municipio", municipio); // Inserta el municipio

        // Inserta los datos en la tabla "favoritos" y devuelve el ID del nuevo registro
        long nuevoID = db.insert("favoritos", null, values);

        db.close(); // Cierra la base de datos
    }

    // Método para eliminar un registro en la tabla "favoritos" basado en el valor de "municipio"
    public void eliminarFavorito(String municipio) {
        SQLiteDatabase db = getWritableDatabase(); // Obtiene una instancia de la base de datos en modo escritura

        // Elimina las filas de la tabla "favoritos" donde el valor de "municipio" coincida con el parámetro proporcionado
        db.delete("favoritos", "municipio = ?", new String[]{municipio});

        db.close(); // Cierra la base de datos
    }

    /**
     * Método para verificar si un municipio específico existe en la tabla "favoritos"
     * @param municipio Municipio que se quiere encontrar
     * @return Devuelve true si existe y false si no lo ha encontrado
     */
    public boolean buscarFavorito(String municipio) {
        SQLiteDatabase db = getReadableDatabase(); // Obtiene una instancia de la base de datos en modo lectura

        // Define las columnas que se quieren recuperar de la tabla, en este caso solo "municipio"
        String[] projection = { "municipio" };

        // Condición de selección para la consulta, donde el municipio debe coincidir con el valor proporcionado
        String selection = "municipio = ?";

        // Argumentos de selección, contiene el valor del municipio que se está buscando
        String[] selectionArgs = { municipio };

        // Realiza una consulta en la tabla "favoritos" para ver si existe el registro del municipio especificado
        Cursor cursor = db.query(
                "favoritos",         // Tabla en la que se realiza la consulta
                projection,          // Columnas a retornar en el resultado de la consulta
                selection,           // Condición de selección de la consulta
                selectionArgs,       // Argumentos de selección (evita inyección SQL)
                null,                // Agrupación (no se usa en este caso)
                null,                // Filtrado de grupos (no se usa en este caso)
                null                 // Orden de los resultados (no se usa en este caso)
        );

        // Verifica si el cursor contiene al menos un resultado, devolviendo true si existe y false si no
        boolean devolver = cursor.getCount() > 0;

        db.close(); // Cierra la base de datos para liberar recursos
        return devolver; // Devuelve el resultado de la búsqueda
    }

    public List<String[]> devuelveFavoritos(){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = { "nombre","provincia","municipio" };
        Cursor cursor = db.query(
                "favoritos",         // Tabla en la que se realiza la consulta
                projection,          // Columnas a retornar en el resultado de la consulta
                null,           // Condición de selección de la consulta
                null,       // Argumentos de selección (evita inyección SQL)
                null,                // Agrupación (no se usa en este caso)
                null,                // Filtrado de grupos (no se usa en este caso)
                null                 // Orden de los resultados (no se usa en este caso)
        );
        List<String[]> favoritos=new ArrayList<>();
        if(cursor.getCount()>0){
            while (cursor.moveToNext()) {
                favoritos.add(new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2)});
            }
            return favoritos;
        }
        return favoritos;
    }

}

/*
Resumen:

    - La clase DatabaseHelper facilita la gestión de la base de datos SQLite.
    - onCreate: Crea la estructura inicial de la tabla favoritos.
    - insertarFavorito: Inserta un nuevo favorito con valores para nombre, provincia y municipio.
    - eliminarFavorito: Elimina un registro específico de la tabla favoritos según el valor de municipio.
    - buscarFavorito: Verifica si un municipio específico existe en la tabla favoritos de la base de datos.
        - Utiliza un Cursor para realizar la consulta, aplicando una condición que filtra los resultados por el nombre del municipio.
        - Resultado: Si el municipio existe, devuelve true; de lo contrario, devuelve false.
*/