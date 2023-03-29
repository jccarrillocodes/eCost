package es.jccarrillo.ecost.core.preferences.data.local

data class LocalPreferences(
    val carName: String,
    val currencyFormat: String,
    val consumptionLabel: String,
    val kwPer100KM: Double,
    val gasReferences: LocalGasReferences
)

data class LocalGasReferences(val diesel: LocalGasReference, val gasoline: LocalGasReference)

data class LocalGasReference(val pricePerLiter: Double, val litersPer100KM: Double)

