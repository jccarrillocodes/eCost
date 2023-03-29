package es.jccarrillo.ecost.core.preferences.domain

data class PreferencesData(
    val carName: String,
    val currencyFormat: String,
    val consumptionLabel: String,
    val kwPer100KM: Double,
    val gasReferences: GasReferences
)

data class GasReferences(val diesel: GasReference, val gasoline: GasReference)

data class GasReference(val pricePerLiter: Double, val litersPer100KM: Double)
