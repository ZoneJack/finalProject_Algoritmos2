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
 * Archivo: AdministradorDeMusica.kt
 */
//============================================================

import java.util.Scanner

/**
 * Módulo Cliente: Administrador de Música (ADM).
 */
fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val miLista = ListaReproduccion()
    
    var miReproductor: Reproductor? = null
    var secuenciaActual: Array<Cancion> = emptyArray()
    var indiceActual: Int = 0
    
    var salir = false

    println("Bienvenido al Administrador de Música (ADM)")

    while (!salir) {
        println("\n--- MENÚ PRINCIPAL ---")
        println("1. Cargar lista de reproducción")
        println("2. Mostrar lista de reproducción")
        println("3. Eliminar canción")
        println("4. Reproducir")
        println("5. Pausar")
        println("6. Parar")
        println("7. Próxima canción")
        println("8. Salir")
        print("Seleccione una opción: ")

        if (scanner.hasNextInt()) {
            val opcion = scanner.nextInt()
            scanner.nextLine() 

            when (opcion) {
                1 -> { // Cargar LR
                    print("Ingrese el nombre del archivo (ruta): ")
                    val ruta = scanner.nextLine().trim()
                    miLista.agregarLista(ruta)
                    // Actualizamos la secuencia por si agregaron canciones nuevas
                    secuenciaActual = miLista.obtenerLR()
                    println("Carga finalizada.")
                }
                2 -> { // Mostrar LR
                    miLista.mostrarLR()
                }
                3 -> { // Eliminar canción
                    print("Ingrese el Intérprete: ")
                    val interprete = scanner.nextLine().trim()
                    print("Ingrese el Título: ")
                    val titulo = scanner.nextLine().trim()
                    
                    // Parar si estamos borrando la canción que suena
                    if (miReproductor != null && miReproductor!!.estaTocandoCancion()) {
                        if (secuenciaActual.isNotEmpty() && 
                            secuenciaActual[indiceActual].interprete == interprete &&
                            secuenciaActual[indiceActual].titulo == titulo) {
                            miReproductor!!.parar()
                            println(" (Reproducción detenida por eliminación)")
                        }
                    }

                    miLista.eliminarCancion(interprete, titulo)
                    secuenciaActual = miLista.obtenerLR()
                    indiceActual = 0 // Reiniciamos índice por seguridad
                }
                4 -> { // Reproducir
                    secuenciaActual = miLista.obtenerLR()
                    
                    if (secuenciaActual.isEmpty()) {
                        println("Error: La lista de reproducción está vacía.")
                    } else {
                        val cancionParaTocar = secuenciaActual[indiceActual]
                        
                        // Si no existe reproductor, lo creamos
                        if (miReproductor == null) {
                            miReproductor = Reproductor(cancionParaTocar)
                        } else {
                            val actual = miReproductor!!.actual
                            
                            if (actual.interprete != cancionParaTocar.interprete ||
                                actual.titulo != cancionParaTocar.titulo) {
                                miReproductor!!.cargarCancion(cancionParaTocar)
                            }
                        }
                        
                        println("Reproduciendo: $cancionParaTocar")
                        miReproductor!!.reproducir()
                    }
                }
                5 -> { // Pausar
                    if (miReproductor != null) {
                        miReproductor!!.pausa()
                        if (secuenciaActual.isNotEmpty()) {
                            println("Pausada: ${secuenciaActual[indiceActual]}")
                        }
                    } else {
                        println("No hay nada reproduciéndose.")
                    }
                }
                6 -> { // Parar
                    if (miReproductor != null) {
                        miReproductor!!.parar()
                        println("Reproducción detenida.")
                    }
                }
                7 -> { // Próxima Canción
                    secuenciaActual = miLista.obtenerLR()
                    if (secuenciaActual.isEmpty()) {
                        println("Lista vacía.")
                    } else {
                        indiceActual = (indiceActual + 1) % secuenciaActual.size
                        val proxima = secuenciaActual[indiceActual]
                        
                        println("Cambiando a: $proxima")
                        
                        if (miReproductor == null) {
                            miReproductor = Reproductor(proxima)
                        } else {
                            miReproductor!!.parar() // Paramos la anterior
                            miReproductor!!.cargarCancion(proxima)
                        }
                        miReproductor!!.reproducir()
                    }
                }
                8 -> { // Salir
                    println("Saliendo del ADM...")
                    if (miReproductor != null) miReproductor!!.parar()
                    salir = true
                }
                else -> println("Opción no válida.")
            }
        } else {
            println("Por favor, ingrese un número.")
            scanner.next()
        }
    }
}
