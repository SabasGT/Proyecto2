package ve.usb.libGrafo

import java.io.File

public class GrafoDirigidoCosto : Grafo {
    private var adjLista: List<MutableList<ArcoCosto>>

    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
        this.adjLista = List(numDeVertices){mutableListOf()}
    }

    /*
     Se construye un grafo a partir de un archivo. El formato es como sigue.
     La primera línea es el número de vértices. La segunda línea es el número de lados. 
     Las siguientes líneas corresponden a los lados. Cada línea de los lados tiene a dos enteros
     que corresponden a los vértices inicial y final de los lados,
     y un número real que es el costo del lado.
     Se asume que los datos del archivo están correctos, no se verifican.
     */  
    constructor(nombreArchivo: String)  {
        var lineas: List<String> = File(nombreArchivo).bufferedReader().readLines()
        val numDeVertices: Int = lineas[0].toInt()
        val numDeAristas: Int = lineas[1].toInt()
        val totalLineas:Int = lineas.size

        this.adjLista =List(numDeVertices){mutableListOf()}

        for (linea in lineas.slice(2..(totalLineas - 1))){
            val lineaDiv = linea.split(' ')
            val a = lineaDiv[0].toInt()
            val b = lineaDiv[1].toInt()
            val c = lineaDiv[2].toDouble()

            this.agregarArcoCosto(ArcoCosto(a,b,c))
        }
    }

    // Agrega un lado al digrafo. Si el lado no encuentra en el grafo se agrega y retorna True,
    // en caso contrario no se agraga al grafo y se retorna false.
    fun agregarArcoCosto(a: ArcoCosto) : Boolean {
        val inicio: Int = a.fuente()
        val fin: Int = a.sumidero()

        if (!this.adjLista[inicio].map{a -> a.sumidero()}.contains(fin)){
            this.adjLista[inicio].add(a)
            return true
        } else {
            return false
        }
    }

    // Retorna el grado del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    override fun grado(v: Int) : Int {
        return (gradoExterior(v) + gradoInterior(v))
    }

    // Retorna el grado exterior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoExterior(v: Int) : Int {
        if ((v < 0) or (v >= this.adjLista.size)){
            throw IllegalArgumentException("Vertice ${v}  no existe en el grafo.")
        }

        return 5
    }

    // Retorna el grado interior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoInterior(v: Int) : Int {
        if ((v < 0) or (v >= this.adjLista.size)){
            throw IllegalArgumentException("Vertice ${v}  no existe en el grafo.")
        }

        return 5
    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int {
        fun long(a: Iterable<Any>): Int {
            return a.fold(0){cont,_: Any -> cont + 1}
        }
        return this.adjLista.map {a -> a.size}.fold(0){cont, n -> cont + n}
    }

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int {
        return this.adjLista.size
    }

    /* 
     Retorna los adyacentes de v, en este caso los lados que tienen como vértice inicial a v. 
     Si el vértice no pertenece al grafo se lanza una RuntimeException
     */
    override fun adyacentes(v: Int) : Iterable<ArcoCosto> {
        if ((v < 0) or (v >= this.adjLista.size)){
            throw IllegalArgumentException("Vertice ${v} no existe en el grafo.")
        }
        return this.adjLista[v]
    }

    // Retorna iterador con todos los lados del digrafo con costo
    override operator fun iterator() : Iterator<ArcoCosto> {

        return adjLista.flatten().iterator()
    }

    // String que muestra el contenido del grafo
    override fun toString() : String {
        return this.adjLista.flatMap{a -> a}.toString()
    }
}
