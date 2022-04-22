package ve.usb.libGrafo

public class ArcoCosto(val x: Int, var y: Int?, var tabX: MutableList<MutableList<Int?>>, var tabY: MutableList<MutableList<Int?>>, var costo: Int?) : Arco(x, y) {

    // Retorna el vértice inicial del arco
    fun x() : Int {
        return this.x
    }

    // Retorna el vértice final del arco
    fun y() : Int? {
        return this.y
    }

    // Retorna el peso o costo asociado del arco
    fun costo() : Int? {
        return this.costo
    }

    // Retorna el tablero pedido. 0 para el tablero 1 y 1 para el tablero 2.
    fun tablero(num: Int) : MutableList<MutableList<Int?>> {
        if (num == 0){
            return this.tabX
        } else {
            return this.tabY
        }
    }

    // Representación del arco
    override fun toString() : String {
        return "(${this.inicio}, ${this.fin}, Tablero 1 = ${this.tabX}, Tablero 2 = ${this.tabY}, costo: ${this.costo}); ${this.inicio} -> ${this.fin} (${this.costo})"
    }

} 
