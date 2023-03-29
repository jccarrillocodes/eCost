package es.jccarrillo.ecost.main.mycar

data class MyCarState(
    val name: String,
    val formatPrice: String,
    val consumptionLabel: String,
    val kwPer100km: String
)

fun emptyMyCarState() = MyCarState(
    name = "",
    formatPrice = "",
    consumptionLabel = "",
    kwPer100km = "0.0"
)