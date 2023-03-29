package es.jccarrillo.ecost.main.mycar

import es.jccarrillo.ecost.core.preferences.domain.PreferencesData

fun PreferencesData.toMyCarState() = MyCarState(
    name = carName,
    formatPrice = currencyFormat,
    consumptionLabel = consumptionLabel,
    kwPer100km = kwPer100KM.toString()
)