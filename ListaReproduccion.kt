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
 * Archivo: ListaRepreduccion.kt
 */
//============================================================

import java.io.File

/**
 * TAD Lista de Reproducción (LR)
 * Estructura dinámica que contiene las canciones a reproducir.
 * Usa internamente un ArbolDeCanciones.
 */
class ListaReproduccion {

    private val contenido: ArbolDeCanciones

    init {
        // Constructor: Crea una LR vacía
        contenido = ArbolDeCanciones()
    }

    /**
     * Lee un archivo de texto y agrega las canciones al árbol.
     * Formato esperado por línea: Interprete;Titulo;Ubicacion
     * na: Nombre del archivo (ruta).
     */
    fun agregarLista(na: String) {
        val archivo = File(na)
        
        if (!archivo.exists()) {
            println("Error: El archivo de lista '$na' no existe.")
            return
        }

        try {
            archivo.forEachLine { linea ->
                // Separamos por punto y coma
                val partes = linea.split(";")
                
                if (partes.size >= 3) {
                    val interprete = partes[0].trim()
                    val titulo = partes[1].trim()
                    val ubicacion = partes[2].trim()
                    
                    try {
                        val nuevaCancion = Cancion(titulo, interprete, ubicacion)
                        
                        val agregado = contenido.agregar(nuevaCancion)
                        if (!agregado) {
                            println("Advertencia: La canción '$titulo' de '$interprete' ya existe en la lista.")
                        }
                    } catch (e: IllegalArgumentException) {
                        println("Advertencia al cargar '$titulo': ${e.message}")
                    }
                }
            }
        } catch (e: Exception) {
            println("Error procesando el archivo de lista: ${e.message}")
        }
    }

    /**
     * Elimina una canción de la lista.
     */
    fun eliminarCancion(interprete: String, titulo: String) {
        val eliminado = contenido.eliminar(interprete, titulo)
        if (eliminado) {
            println("Canción eliminada exitosamente.")
        } else {
            println("Error: No se encontró la canción '$titulo' de '$interprete'.")
        }
    }

    /**
     * Retorna una secuencia con todas las canciones ordenadas.
     */
    fun obtenerLR(): Array<Cancion> {
        return contenido.obtenerSecuencia()
    }

    /**
     * Muestra por pantalla todas las canciones de la lista.
     */
    fun mostrarLR() {
        val secuencia = contenido.obtenerSecuencia()
        
        if (secuencia.isEmpty()) {
            println("La lista de reproducción está vacía.")
        } else {
            println("=== Lista de Reproducción ===")
            for (cancion in secuencia) {
                println(cancion.toString())
            }
            println("Total: ${secuencia.size} canciones.")
        }
    }
}