package es.jccarrillo.ecost.core.preferences.data.mapper

import es.jccarrillo.ecost.core.preferences.data.local.LocalGasReference
import es.jccarrillo.ecost.core.preferences.data.local.LocalGasReferences
import es.jccarrillo.ecost.core.preferences.data.local.LocalPreferences
import es.jccarrillo.ecost.core.preferences.domain.GasReference
import es.jccarrillo.ecost.core.preferences.domain.GasReferences
import es.jccarrillo.ecost.core.preferences.domain.PreferencesData

/* toDomain */

fun LocalPreferences.toDomain() = PreferencesData(
    carName = carName,
    currencyFormat = currencyFormat,
    consumptionLabel = consumptionLabel,
    kwPer100KM = kwPer100KM,
    gasReferences = gasReferences.toDomain()
)

fun LocalGasReferences.toDomain() = GasReferences(
    diesel = diesel.toDomain(),
    gasoline = gasoline.toDomain()
)

fun LocalGasReference.toDomain() = GasReference(
    pricePerLiter = pricePerLiter,
    litersPer100KM = litersPer100KM
)

/* toData */

fun PreferencesData.toData() = LocalPreferences(
    carName = carName,
    currencyFormat = currencyFormat,
    consumptionLabel = consumptionLabel,
    kwPer100KM = kwPer100KM,
    gasReferences = gasReferences.toData()
)

fun GasReferences.toData() = LocalGasReferences(
    diesel = diesel.toData(),
    gasoline = gasoline.toData()
)

fun GasReference.toData() = LocalGasReference(
    pricePerLiter = pricePerLiter,
    litersPer100KM = litersPer100KM
)