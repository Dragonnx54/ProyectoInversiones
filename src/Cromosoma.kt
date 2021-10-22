class Cromosoma(private val cromosoma: String
                ,var genotipo1:String=cromosoma.substring(0..3)
                ,var genotipo2:String=cromosoma.substring(4..7)
                ,var genotipo3:String=cromosoma.substring(8..11)
                ,var genotipo4:String=cromosoma.substring(12..15)){
    var hashMap : HashMap<Int, String> = HashMap<Int, String> ()
    val beneficios1: DoubleArray = doubleArrayOf(0.00,0.28,0.45,0.65,0.78,0.90,1.02,1.13,1.23,1.32,1.38)
    val beneficios2: DoubleArray = doubleArrayOf(0.00,0.25,0.41,0.55,0.65,0.75,0.80,0.85,0.88,0.90,0.90)
    val beneficios3: DoubleArray = doubleArrayOf(0.00,0.15,0.25,0.40,0.50,0.62,0.73,0.82,0.90,0.96,1.00)
    val beneficios4: DoubleArray = doubleArrayOf(0.00,0.20,0.33,0.42,0.48,0.53,0.56,0.58,0.60,0.60,0.60)
    init{
        hashMap.put(1,genotipo1)
        hashMap.put(2,genotipo2)
        hashMap.put(3,genotipo3)
        hashMap.put(4,genotipo4)
    }
    fun binaryToInt(pos: Int): Int {
        var genotipo: String? = hashMap.get(pos)
        return Integer.parseInt(genotipo,2)
    }

    fun aptitud():Float{
        var suma:Double=beneficios1[binaryToInt(1)]+beneficios2[binaryToInt(2)]+beneficios3[binaryToInt(3)]+beneficios4[binaryToInt(4)]
        var suma2:Int= binaryToInt(1)+binaryToInt(2)+binaryToInt(3)+binaryToInt(4)
        return ((suma).toFloat()/(((500*Math.abs(suma2 - 10))+1)).toFloat())
    }

    fun inversiones():String{
        return "Inversion ----->Mexico=${Integer.parseInt(genotipo1,2)},Monterrey=${Integer.parseInt(genotipo2,2)}" +
                ",Guadalajara=${Integer.parseInt(genotipo3,2)},Veracruz=${Integer.parseInt(genotipo4,2)} "
    }

    override fun toString():String{
        return "${genotipo1}${genotipo2}${genotipo3}${genotipo4}"
    }
}