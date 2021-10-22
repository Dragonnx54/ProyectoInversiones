import kotlin.collections.ArrayList


const val GENERACIONES:Int=20
const val POBLACION:Int=50
const val PROBABILIDADCRUZA: Int=80
const val PROBABILIDADMUTA:Int=1
const val TORNEOS=50

fun main(){
    var poblacion= ArrayList<Cromosoma>()
    for (i in 1..POBLACION){
        poblacion.add(Cromosoma(generaBinario(4)))
    }
    println("POBLACION INICIAL")
    poblacion.forEach {
        println("${it} ------->Aptitud=${it.aptitud()}   ${it.inversiones()}")
    }
    println("---------------------")

    var nuevaGeneracion= ArrayList<Cromosoma>()

    for(i in 1..GENERACIONES){
        nuevaGeneracion.clear()
        //SELECCION POR TORNEO
        for (j in (1..TORNEOS)){
            val rand1=(0 until poblacion.size).random()
            var rand2=(0 until poblacion.size).random()
            while (rand1==rand2)
                rand2=(0 until poblacion.size).random()

            val cromosoma1:Cromosoma = poblacion.get(rand1)
            val cromosoma2:Cromosoma = poblacion.get(rand2)

            if(cromosoma1.aptitud()>cromosoma2.aptitud())
                nuevaGeneracion.add(cromosoma1)
            else
                nuevaGeneracion.add(cromosoma2)
        }


        //Cruza 80%
        for (j in 0 until poblacion.size step 2){
            var random= (1..100).random()
            if (random < PROBABILIDADCRUZA) {
                val padre1 = poblacion.get(j)
                val padre2 = poblacion.get(j + 1)
                val lista = cruzaDosPuntos(padre1, padre2)
                nuevaGeneracion.set(j, lista.get(0))
                nuevaGeneracion.set(j + 1, lista.get(1))
            }
        }

        //MUTACION 1%
        for (j in 0 until poblacion.size){
            var random= (1..100).random()
            if (random == PROBABILIDADMUTA) {
                val mutante=nuevaGeneracion.get(j)
                nuevaGeneracion.set(j,muta(mutante))
            }
        }


        println("GENERACION #${i}")
        var listaFalsa= nuevaGeneracion.sortedByDescending { it.aptitud()}
        listaFalsa.forEach {
            println("${it} ------->Aptitud=${it.aptitud()}   ${it.inversiones()}")
        }
        poblacion= ArrayList(nuevaGeneracion)
    }
}

//GENERADOR DE NUMEROS BINARIOS [PROPORCIONA EL NUMERO DE BITS DEL NUMERO GENERADO]
fun generaBinario(bytes:Int):String{
    var suma=0
    var binario:String=""
    var aleatorio:Int
    while (suma!=10){
        suma=0
        binario=""
        for(i in 1..bytes){
            aleatorio=(0..10).random()
            suma+=aleatorio
            var aux="${Integer.toBinaryString(aleatorio)}"
            while(aux.length<4){
                aux="0$aux"
            }
            binario+=aux
        }
    }
    return binario
}

//METODO DE CRUZA POR DOS PUTNOS
fun cruzaDosPuntos(padre1:Cromosoma,padre2:Cromosoma):Array<Cromosoma>{
    //PUNTO DE CRUZA 4 y 12
    val hijo1:String= padre1.genotipo1  +padre2.genotipo2+padre2.genotipo3  +padre1.genotipo4
    val hijo2:String= padre2.genotipo1  +padre1.genotipo2+padre1.genotipo3  +padre2.genotipo4

    return arrayOf<Cromosoma>(Cromosoma(hijo1),Cromosoma(hijo2))
}

//METODO MUTACION SIMPLE
fun muta(cromosoma: Cromosoma):Cromosoma{
    while(true) {
        var randgenotipo = (1..4).random()
        var genotipo: String = ""
        when (randgenotipo) {
            1 -> genotipo = cromosoma.genotipo1
            2 -> genotipo = cromosoma.genotipo2
            3 -> genotipo = cromosoma.genotipo3
            4 -> genotipo = cromosoma.genotipo4
        }

        var randPos = (0..3).random()
        var numero: String = cromosoma.toString()[randPos].toString()
        if (numero.equals("1"))
            numero = "0"
        else
            numero = "1"

        when(randPos){
            0->{
                genotipo="${numero}${genotipo.substring(1,4)}"
            }
            3->{
                genotipo="${genotipo.substring(0,3)}${numero}"
            }
            else -> {
                genotipo="${genotipo.substring(0,randPos)}${numero}${genotipo.substring(randPos+1,4)}"
            }
        }
        if(Integer.parseInt(genotipo,2) > 10){
            continue
        }
        when (randgenotipo) {
            1 -> cromosoma.genotipo1 = genotipo
            2 -> cromosoma.genotipo2 = genotipo
            3 -> cromosoma.genotipo3 = genotipo
            4 -> cromosoma.genotipo4 = genotipo
        }
        break;
    }
    return cromosoma
}
