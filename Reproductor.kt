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
 * Archivo: Reproductor.kt
 */
//============================================================

import java.io.FileInputStream
import java.io.BufferedInputStream

/**
 * TAD Reproductor
 * Encargado de la reproducción de canciones usando PausablePlayer.
 */
class Reproductor(c: Cancion) {

    var actual: Cancion = c

    private var player: PausablePlayer? = null
    
    // Indica si la canción se está reproduciendo activamente
    private var reproduciendo: Boolean = false

    // Constructor
    init {
        // Post: self.actual = c
        // El player inicia nulo hasta que se mande a reproducir
    }

    /**
     * Carga una nueva canción en el reproductor.
     * Si había una sonando, la detiene.
     */
    fun cargarCancion(c: Cancion) {
        if (player != null) {
            parar()
        }
        this.actual = c
        this.player = null
        this.reproduciendo = false
    }

    /**
     * Reproduce la canción actual.
     * - Si estaba detenida o es nueva: Inicia desde el principio.
     * - Si estaba pausada: Reanuda desde donde quedó.
     */
    fun reproducir() {
        try {
            // Si el player es nulo (inicio o después de stop), creamos uno nuevo
            if (player == null) {
                val fis = FileInputStream(actual.ubicacion)
                val bis = BufferedInputStream(fis)
                player = PausablePlayer(bis)
            }
            
            player!!.play()
            reproduciendo = true
            
        } catch (e: Exception) {
            println("Error al reproducir el archivo: ${e.message}")
            reproduciendo = false
        }
    }

    /**
     * Detiene la reproducción.
     * La próxima vez que se llame a reproducir, empezará desde el inicio.
     */
    fun parar() {
        if (player != null) {
            player!!.stop()
            player!!.close()
            player = null 
        }
        reproduciendo = false
    }

    /**
     * Pausa la reproducción actual.
     */
    fun pausa() {
        if (player != null) {
            player!!.pause()
        }
        reproduciendo = false
    }

    /**
     * Retorna True si el audio se está escuchando activamente.
     */
    fun estaTocandoCancion(): Boolean {
        return reproduciendo
    }
}