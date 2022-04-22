import ve.usb.libGrafo.*
import java.io.File

// Funcion que revisa el tablero introducido y comprueba si es posible resolverlo
fun resolvible(archivo: File): Boolean {
    var listaNum: MutableList<Int> = mutableListOf()

    // Guardamos la secuencia de numeros en una lista
        archivo.forEachLine() {
            var lineaDiv: List<String> = it.split(" ")

            listaNum.add(lineaDiv[0].toInt())
            listaNum.add(lineaDiv[1].toInt())
            listaNum.add(lineaDiv[2].toInt())         
        }

    // Comparamos los elementos en la lista para ver la cantidad de inversiones (Cantidad Par = Es Posible resolverlo; Cantidad Impar = No se puede resolver)
    var inversiones: Int = 0    // Contador de inversiones

    for (i in 0..7) {
        for (j in i..8){
            if (listaNum[i] > listaNum[j] && listaNum[j] != 0) {
                inversiones++
            }
        }
    }

    if (inversiones % 2 == 0){
        println("Cantidad de Inversiones: $inversiones.")
        return true
    } else {
        println("Cantidad de Inversiones: $inversiones.")
        return false
    }
}


fun main(args: Array<String>) {
    
    // Revisar si se introdujo la cantidad (2) adecuada de argumentos en la linea de comando.
    if (args.size == 0 || args.size == 1 || args.size > 2){
        throw RuntimeException("No se introdujo la cantidad (2) necesaria de argumentos.")
    }

    // Se extraen los valores de la linea de comando.
    var flagEuristica = args[0].toString()
    var archivo =  args[1].toString()

    println("La flag es $flagEuristica y el archivo es $archivo")

    // Apertura de Archivo. 
    val arch: File = File(archivo)

    if (!arch.exists()){
            throw RuntimeException("No se encontro el archivo ${archivo}")
        }

    if (resolvible(arch) == true) {
        val inicio = System.nanoTime()  // Comenzar a medir el tiempo de ejecucion.
        println("El tablero tiene solucion.")

        // Extraer el primer tablero del archivo
        var tabIni: MutableList<MutableList<Int?>> = MutableList(0){mutableListOf()}
        var tabNull: MutableList<MutableList<Int?>> = mutableListOf(mutableListOf(null,null,null), mutableListOf(null,null,null), mutableListOf(null,null,null))

        arch.forEachLine() {
            var lineaDiv: List<String> = it.split(" ")

            tabIni.add(mutableListOf(lineaDiv[0].toInt(), lineaDiv[1].toInt(), lineaDiv[2].toInt()))
        }

        var grafo = GrafoDirigidoCosto()
        var arco = ArcoCosto(0, null, tabIni, tabNull, null)
        grafo.agregarArcoCosto(arco)
        val fin = System.nanoTime()

        // FIN DEL PROGRAMA
        var tiempo: Long = (fin - inicio)/1000000   // Fin del tiempo de ejecucion.

        println("Tiempo: $tiempo ms")
    } else {
        // FIN DEL PROGRAMA
        println("El tablero no tiene solucion.")
    }
}