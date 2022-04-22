package ve.usb.libGrafo

abstract class Lado(val a: Int, val b: Int?) {

    // Retorna cualquiera de los dos vértices del grafo
    fun cualquieraDeLosVertices() : Int {
        return this.a
    }

    // Dado un vertice w, si w == a entonces retorna b, de lo contrario si w == b
    // entonces retorna a,  y si w no es igual a a ni a b, entonces se lanza una RuntimeExpception 
    fun elOtroVertice(w: Int) : Int? {
        if ((w != a) and (w != b) ){
            throw IllegalArgumentException("Vertice ${w} no existe en el grafo.")
            }
        else if (w == a){
            return b
        } else {
            return a
        }   
    }
}
