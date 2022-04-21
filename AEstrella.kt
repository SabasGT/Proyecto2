package ve.usb.libGrafo

/*
 Implementación del algoritmo de A* para encontrar un camino
 desde un vértice fuente s fijo hasta alguno de los vértices objetivos. 
 La implementación debe usar como cola de prioridad un min-heap.
 
 Los parármetros de entradas son los siguientes:
 g: Digrafo con los costos en los lados. 
 s: Vértice de partida.
 objs: Conjunto de vértices objetivos. 
 h: función estimativa de h. Recibe como entrada un vértice v y retorna 
    el costo estimado desde ese vértice v, hasta cualquiera de los vértices 
    de la meta en objs.

 Como precondiciones se tiene que:
    * Si el grafo de entrada tiene un lado con costo negativo,  entonces se retorna una RuntimeException.
    * Si el vértice s no pertenece al grafo, entonces se retorna una RuntimeException.
    * Si alguno de los vértices en objs no pertenece al grafo, entonces se retorna una RuntimeException.
    * Al menos uno de los vértices de objs debe ser alcanzable desde s. Esta precondición no se chequea por razones de eficiencia.
*/
public class AEstrella(val g: GrafoDirigidoEntradaJM, 
		       val s: Int,
		       val objs: Set<Entrada>,
		       val h: (Entrada) -> Double) {

   var listaArcos = g.iterator()
   var numVerticesG = g.obtenerNumeroDeVertices()
   var listaDeVerticesG = g.vertices()
   var arrayF = ArrayList<Double>()
   var predecesores = ArrayList<Int?>()
   var objetivo = 0 // variable para guardar el ultimo vertice alcanzado
   var camino = mutableListOf<ArcoCosto>()

    init {

        /*
       descripcion: Funcion que corre el algoritmo de A*.
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase grafoDirigidoCosto
        con vertice s de partida, perteneciente al digrafo, Set de vertices objetivo de la busqueda desde
        s hasta ellos, tal que cada v pertenenciente a objs pertenece al digrafo, y una funcion 
        h tal que pase un int vertice y se obtenga un double.

        postcondiciones: camino de costo minimo desde s hasta algun vertice v perteneciente a objs

        tiempo de la operacion: O(E*log(V)) por usar una cola de prioridad implementada como un
        min Heap.

     */

       // Que no tenga lados negativos.
      this.listaArcos.forEach { arco ->
         if (arco.cost < 0.0) {
            throw RuntimeException("El digrafo tiene un lado negativo, ${arco}")
         }
      }

      // Si s no pertenece al digrado 
      if (!(s in this.listaDeVerticesG)) {
         throw RuntimeException("El vertice de partida $s no pertenece al digrafo")
      }

      /* 
      // Si uno de los vertices objetivos no pertenece al digrafo.
      objs.forEach { vertice ->
         if (!(vertice in this.listaDeVerticesG)) {
            throw RuntimeException("El vertice objetivo ${vertice} no pertenece al digrafo")
         }
      }
      */

      //Si nada de lo anterior falla, inicializamos A*

      // Creamos la cola, u , iniciamos la distancia de s en 0 y la lista de vertices.
        var closed : MutableList<Int> = mutableListOf() // closed
        var queue : MutableList<Int> = mutableListOf() // open
        var uAnterior = 0 // var para poder retornar el ultimo valor antes de la meta y colocarlo como pred[meta]
        // inicializamos la distancia de s.
       this.predecesores.add(s,null)
        this.arrayF.add(s,h(g.listaTrad[s].second))
        // para la lista inicial, que es inicial y meta
        
      
        // Insertamos s
        queue.add(s)
        heap(queue)

        var u = extraerMin(queue)
        var uEntrada = g.listaTrad[u].second.array
   
        while (!(uEntrada.contentEquals(objs.first().array))) {
           
           closed.add(u)
         //  println("En A* busca sucesores de ${u} y su pred es ${this.predecesores[u]}")
           if (u == 0) {
              g.sucesores(u,-1)
           } else {
             // println("Predecesores en A* ${this.predecesores.joinToString()}")
              g.sucesores(u,this.predecesores[u] as Int)
           }
           
            g.adyacentes(u).forEach { arco ->
               var primerV = arco.primerV
               var segundoV = arco.segundoV
               var costo = arco.cost
               if (!(segundoV in closed)) {
                  var newF = this.arrayF[u] - h(g.listaTrad[u].second) + costo + h(g.listaTrad[segundoV].second)
                  if (!(segundoV in queue)) { // v no esta en OPEN
                     this.arrayF.add(newF)
                     this.predecesores.add(u)
                     // Insert in queue
                     queue.add(segundoV)
                     heap(queue)
                  } else {
                     if (newF < this.arrayF[segundoV]) {
                        this.arrayF.set(segundoV,newF)
                        this.predecesores.set(segundoV,u)
                        decrementarCosto(queue,segundoV)
                     }
                  }
               }
            }
            uAnterior = u
            u = extraerMin(queue)
            uEntrada = g.listaTrad[u].second.array
            this.objetivo = u
          //  println("este es uAnterior : ${uAnterior} y este es u ${u}")
        }

      println("termino el while de A*\n")
      println("Predecesores ${this.predecesores.joinToString()}\n")
      println("CostoF ${this.arrayF.joinToString()}\n")
      println("El ultimo en entrar fue ${this.objetivo}\n")
      println("El objetivo da como resultado en lista de trad ${g.listaTrad[this.objetivo]}\n")
    }
    
    
     /*
       descripcion: Funcion que determina obtiene vertice objetivo alcanzado desde s
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase AEstrella

        postcondiciones: int que representa al vertice objetivo alcanzado

        tiempo de la operacion: O(1) por ser una asginacion.

     */
   
   fun objetivoAlcanzado() : Int { 
      return this.objetivo
   }
   

    // Retorna el costo del camino desde s hasta el vértice objetivo alcanzado.
      /*
       descripcion: costo del camino desde s hasta el vertice objetivo
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase AEstrella

        postcondiciones: Double que representa el coste del camino encontrado

        tiempo de la operacion: si el camino ya ha sido creado es O(E) por recorrer todos los arcos,
        para obtener el costo total del camino, en otro caso es O(E+V) por crear el camino. 

     */ 
    fun costo() : Double {
       var costoTotal =  0.0
       if (this.camino.isEmpty()) {
          var caminoEncontrado = this.obtenerCamino()
          caminoEncontrado.forEach { arco ->
            costoTotal = costoTotal + arco.cost
          }
       } else {
          this.camino.forEach { arco ->
            costoTotal = costoTotal + arco.cost
          }
       }
       return costoTotal
    }

   
      /*
       descripcion: Funcion que retorna el camino desde s hasta el vertice objetivo
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase AEstrella

        postcondiciones: lista que representa el camino encontrado

        tiempo de la operacion: si el camino ya ha sido creado es O(1) por asignacion,
        si no ha sido creado es O(V) por recorrer todos los predecesores hasta s.

     */
   fun obtenerCamino() : Iterable<ArcoCosto> { 
      if (this.camino.isEmpty()) {
         var  listaCamino = mutableListOf<ArcoCosto>()
         var verticeAnterior = this.objetivo
         var fuenteAnterior = 0
         var arcos = g.arcos()

         while(this.predecesores[verticeAnterior] as Int != s) {
            var proximoVertice = this.predecesores[verticeAnterior] as Int
            var arcoEncontrado = arcos.find { a -> (a.primerV == proximoVertice && a.segundoV ==  verticeAnterior) }
            println("Proximo vertice : ${proximoVertice} , vertice anterior : ${verticeAnterior} y arco encontrado : ${arcoEncontrado}")
            var  newArco = ArcoCosto(arcoEncontrado?.primerV as Int,arcoEncontrado?.segundoV as Int,arcoEncontrado?.cost as Double)
            listaCamino.add(newArco)
            verticeAnterior = proximoVertice
            }
         var arcoEncontradoFinal = arcos.find { a -> (a.primerV == s && a.segundoV ==  verticeAnterior) }
         var ultimoArco = ArcoCosto(arcoEncontradoFinal?.primerV as Int,arcoEncontradoFinal?.segundoV as Int,arcoEncontradoFinal?.cost as Double)
         listaCamino.add(ultimoArco)
         var listaInversa = listaCamino.reversed()
         this.camino = listaInversa.toMutableList()
         return listaInversa.asIterable()
      } else {
         return this.camino
      }
   }

     // Implementacion min-heap
       
    fun minHeapify (queue:MutableList<Int>, i:Int, n:Int) {
        val left:Int = 2*i
        val right:Int = 2*i + 1
        val iW:Double =  this.arrayF[queue.get(i)]
        var min:Int?
        if(left<= n && (this.arrayF[queue.get(left)])< iW ){
              min = left
            }
        else{
            min = i
        }

        val minW = this.arrayF[queue.get(min)]
        if(right <= n && (this.arrayF[queue.get(right)]) < minW )
            min = right
        if(min != i )
        {
            queue.set(i, queue.get(i) + queue.get(min))
            queue.set(min, queue.get(i) - queue.get(min))
            queue.set(i, queue.get(i) - queue.get(min))
            minHeapify(queue, min, n)
        } 
     }

  
    fun heap(queue:MutableList<Int>)  {
        for(i in queue.size/2 downTo 0){
            minHeapify (queue, i, queue.size-1)
        }
    }


     fun extraerMin(queue:MutableList<Int>):Int {
        val u = queue.get(0)
        queue.set(0, queue.get(queue.size-1))
        queue.removeAt(queue.size-1)
        if (queue.size != 0) { 
            minHeapify(queue, 0, queue.size-1)
        }
        return u
    }

    fun decrementarCosto(queue:MutableList<Int>, v:Int) {
        var i = queue.indexOf(v)
        while(i>0 && this.arrayF[queue.get(i/2)]  > this.arrayF[queue.get(i)] ) {
            
            queue.set(i, queue.get(i) + queue.get(i/2))
            queue.set(i/2, queue.get(i) - queue.get(i/2))
            queue.set(i, queue.get(i) - queue.get(i/2))
            i = i/2
        }
    }
}
