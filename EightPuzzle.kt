import ve.usb.libGrafo.*
import java.io.File
import kotlin.math.abs

/*  Funcion que revisa el tablero introducido y comprueba si es posible resolverlo
    Parametros: 
                archivo: Documento de texto ya abierto
    Precondicion:
                Ninguna
    Postcondicion:
                Se retorna un booleano Verdadero si el tablero 8 Puzzle extraido del archivo tiene solución
                O Falso si no tiene solucion.
*/
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

fun heuristica(modo: String, tablero: MutableList<MutableList<Int?>>): Int {
    var valor: Int = 0

    when (modo){
        "z" -> {    // Retorna 0
            return valor
        }
        "d" -> {    // Numero de Cuadros Desordenados
            val orden: List<List<Int>> = listOf(listOf(1,2,3), listOf(4,5,6), listOf(7,8,0))

            if (orden[0][0] !== tablero[0][0]){
                valor++
            }

            if (orden[0][1] !== tablero[0][1]){
                valor++
            }
            if (orden[0][2] !== tablero[0][2]){
                valor++
            }
            if (orden[1][0] !== tablero[1][0]){
                valor++
            }

            if (orden[1][1] !== tablero[1][1]){
                valor++
            }
            if (orden[1][2] !== tablero[1][2]){
                valor++
            }
            if (orden[2][0] !== tablero[2][0]){
                valor++
            }

            if (orden[2][1] !== tablero[2][1]){
                valor++
            }
            return valor
        }
        "m" -> {    // Distancia Manhattan
            for (num in 1..8){
                for (i in 0..2){
                    for (j in 0..2){
                        if (tablero[i][j] == num){
                            when (num){
                                1 -> valor = valor + abs(0 - i) + abs(0 - j)
                                2 -> valor = valor + abs(0 - i) + abs(1 - j)
                                3 -> valor = valor + abs(0 - i) + abs(2 - j)
                                4 -> valor = valor + abs(1 - i) + abs(0 - j)
                                5 -> valor = valor + abs(1 - i) + abs(1 - j)
                                6 -> valor = valor + abs(1 - i) + abs(2 - j)
                                7 -> valor = valor + abs(2 - i) + abs(0 - j)
                                8 -> valor = valor + abs(2 - i) + abs(1 - j)
                            }
                        }
                    }
                }
            }
            return valor
        }
        "b" -> {    // Distancia Blanco
            for (i in 0..2){
                for (j in 0..2){
                    if (tablero[i][j] == 0){
                        valor = abs(2 - i) + abs(2 - j)
                    }
                }
            }

            return valor
        }
        else -> throw RuntimeException("El modo introducido de Heuristica no es valido.")
    }

    return valor
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