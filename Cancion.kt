/**
 * Universidad Simon Bolivar
 * Departamento de Computacion y Tecnologia de la Informacion
 * CI2692 - Laboratorio de Algoritmos y Estructuras 2
 * Estudiantes:
 *            Arianna Martinez, carnet: 21-10358
 *            Jose Torbet, carnet: 21-10650
 */
//============================================================
/**
 * Proyecto: Administrador de musica
 * Archivo: Cancion.kt
 */
//============================================================

import java.io.File

/**
 * TAD Cancion
 * Representa la informacion relacionada con un archivo de audio MP3
 */
class Cancion(
    private val tituloParam: String,
    private val interpreteParam: String,
    private val ubicacionParam: String
) {

    // Propiedades públicas para lectura
    val titulo: String
    val interprete: String
    val ubicacion: String

    /*
     * Constructor: 
     * Verifica las precondiciones antes de asignar valores.
     */
    init {
        
        // Pre: t != NULL ^ i != NULL ^ u != NULL ^ esUbicacionValida(u)
        
        if (!esUbicacionValida(ubicacionParam)) {
            throw IllegalArgumentException("Error: La ubicación '$ubicacionParam' no es válida o el archivo no existe.")
        }

        this.titulo = tituloParam
        this.interprete = interpreteParam
        this.ubicacion = ubicacionParam

        // Post: self.titulo = t ^ self.interprete = i ^ self.ubicacion = u
    }

    /**
     * Verifica si una ubicación corresponde a un archivo existente.
     * u: dirección absoluta del archivo.
     * retorna True si el archivo existe, False en caso contrario.
     */
    private fun esUbicacionValida(u: String): Boolean {
        val archivo = File(u)
        return archivo.exists() && archivo.isFile
    }

    /**
     * Retorna el título de la canción.
     * Pre: True
     * Post: obtenerTitulo = self.titulo
     */
    fun obtenerTitulo(): String {
        return this.titulo
    }

    /**
     * Retorna el intérprete de la canción.
     * Pre: True
     * Post: obtenerInterprete = self.interprete
     */
    fun obtenerInterprete(): String {
        return this.interprete
    }

    /**
     * Retorna la ubicación del archivo de audio.
     * Pre: True
     * Post: obtenerUbicacion = self.ubicacion
     */
    fun obtenerUbicacion(): String {
        return this.ubicacion
    }

    /**
     * Genera una representación en String de la canción.
     * Pre: True
     * Post: toString = String que muestra el self.titulo y el self.interprete
     */
    override fun toString(): String {
        return "$interprete - $titulo"
    }
}