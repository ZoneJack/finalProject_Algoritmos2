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
 * Archivo: ArbolDeCanciones.kt
 */
//============================================================

/**
 * TAD ArbolDeCanciones
 * Representa un árbol binario de búsqueda que almacena objetos de tipo Cancion.
 * Ordena las canciones por Interprete (alfabéticamente) o por Título si los interpretes son iguales.
 */
class ArbolDeCanciones {
    
    /**
     * Clase interna Nodo.
     * Contiene la cancion y referencias a hijo izquierdo, derecho y padre.
     */
    class Nodo(var cancion: Cancion) {
        var izquierdo: Nodo? = null
        var derecho: Nodo? = null
        var padre: Nodo? = null
    }

    private var raiz: Nodo? = null

    /**
     * Constructor: Crea un árbol vacío.
     */
    init {
        // Post: raiz = null
    }
    
    fun esVacio(): Boolean {
        return raiz == null
    }

    /**
     * Agrega una canción al árbol manteniendo el orden.
     * c: Canción a agregar.
     * retorna True si se agregó, False si ya existía.
     */
    fun agregar(c: Cancion): Boolean {
        if (raiz == null) {
            raiz = Nodo(c)
            return true
        }

        var actual = raiz
        var padre: Nodo? = null
        var comparacion = 0

        // Buscar la posición de inserción
        while (actual != null) {
            padre = actual
            comparacion = comparar(c, actual.cancion)

            if (comparacion < 0) {
                actual = actual.izquierdo
            } else if (comparacion > 0) {
                actual = actual.derecho
            } else {
                // La canción ya existe (duplicada)
                return false
            }
        }

        // Insertar el nuevo nodo
        val nuevoNodo = Nodo(c)
        nuevoNodo.padre = padre

        if (comparacion < 0) {
            padre?.izquierdo = nuevoNodo
        } else {
            padre?.derecho = nuevoNodo
        }
        return true
    }

    /**
     * Busca una canción por intérprete y título.
     */
    fun buscar(interprete: String, titulo: String): Cancion? {
        val nodo = buscarNodo(interprete, titulo)
        return nodo?.cancion
    }

    /**
     * Elimina una canción del árbol dado su intérprete y título.
     */
    fun eliminar(interprete: String, titulo: String): Boolean {
        val nodo = buscarNodo(interprete, titulo) ?: return false
        eliminarNodo(nodo)
        return true
    }

    /**
     * Retorna todas las canciones en orden.
     */
    fun obtenerSecuencia(): Array<Cancion> {
        val cantidad = contarNodos(raiz)
        if (cantidad == 0) return emptyArray()
                
        val resultado = arrayOfNulls<Cancion>(cantidad)
        var indice = 0
        
        fun inOrden(nodo: Nodo?) {
            if (nodo != null) {
                inOrden(nodo.izquierdo)
                resultado[indice++] = nodo.cancion
                inOrden(nodo.derecho)
            }
        }
        inOrden(raiz)
        
        // Casting seguro ya que sabemos que no hay nulos en el array final
        @Suppress("UNCHECKED_CAST")
        return resultado as Array<Cancion>
    }

    //--- Funciones auxiliares ---

    private fun comparar(c1: Cancion, c2: Cancion): Int {
        val interp = c1.interprete.compareTo(c2.interprete)
        if (interp != 0) return interp
        return c1.titulo.compareTo(c2.titulo)
    }

    private fun buscarNodo(interprete: String, titulo: String): Nodo? {
        var actual = raiz
        while (actual != null) {
            val cmpInterprete = interprete.compareTo(actual.cancion.interprete)
            if (cmpInterprete < 0) {
                actual = actual.izquierdo
            } else if (cmpInterprete > 0) {
                actual = actual.derecho
            } else {
                val cmpTitulo = titulo.compareTo(actual.cancion.titulo)
                if (cmpTitulo < 0) {
                    actual = actual.izquierdo
                } else if (cmpTitulo > 0) {
                    actual = actual.derecho
                } else {
                    return actual
                }
            }
        }
        return null
    }

    private fun eliminarNodo(z: Nodo) {
        if (z.izquierdo == null) {
            transplantar(z, z.derecho)
        } else if (z.derecho == null) {
            transplantar(z, z.izquierdo)
        } else {
            // Caso con dos hijos: buscar el sucesor (mínimo del subárbol derecho)
            val y = minimoNodo(z.derecho!!)
            if (y.padre != z) {
                transplantar(y, y.derecho)
                y.derecho = z.derecho
                y.derecho?.padre = y
            }
            transplantar(z, y)
            y.izquierdo = z.izquierdo
            y.izquierdo?.padre = y
        }
    }

    private fun transplantar(u: Nodo, v: Nodo?) {
        if (u.padre == null) {
            raiz = v
        } else if (u == u.padre?.izquierdo) {
            u.padre?.izquierdo = v
        } else {
            u.padre?.derecho = v
        }
        v?.padre = u.padre
    }

    private fun minimoNodo(x: Nodo): Nodo {
        var actual = x
        while (actual.izquierdo != null) {
            actual = actual.izquierdo!!
        }
        return actual
    }

    private fun contarNodos(nodo: Nodo?): Int {
        if (nodo == null) return 0
        return 1 + contarNodos(nodo.izquierdo) + contarNodos(nodo.derecho)
    }

    private fun esArbolDeBusqCancion(nodo: Nodo?): Boolean {
        if (nodo == null) return true
        val c = nodo.cancion
        val izq = nodo.izquierdo
        val der = nodo.derecho

        val condDerecha = if (der == null) true else {
            val minInterp = minInterprete(der)
            val minTit = minTitulo(der)

            (c.interprete < minInterp) ||
            (c.interprete == minInterp && c.titulo < minTit)
        }

        val condIzquierda = if (izq == null) true else {
            val maxInterp = maxInterprete(izq)
            val maxTit = maxTitulo(izq)

            (c.interprete > maxInterp) ||
            (c.interprete == maxInterp && c.titulo > maxTit)
        }

        return condDerecha && condIzquierda &&
               esArbolDeBusqCancion(izq) && esArbolDeBusqCancion(der)
    }

    private fun minInterprete(nodo: Nodo): String {
        var actual = nodo
        while (actual.izquierdo != null) {
            actual = actual.izquierdo!!
        }
        return actual.cancion.interprete
    }

    private fun maxInterprete(nodo: Nodo): String {
        var actual = nodo
        while (actual.derecho != null) {
            actual = actual.derecho!!
        }
        return actual.cancion.interprete
    }

    private fun minTitulo(nodo: Nodo): String {
        var actual = nodo
        while (actual.izquierdo != null) {
            actual = actual.izquierdo!!
        }
        return actual.cancion.titulo
    }

    private fun maxTitulo(nodo: Nodo): String {
        var actual = nodo
        while (actual.derecho != null) {
            actual = actual.derecho!!
        }
        return actual.cancion.titulo
    }
}
