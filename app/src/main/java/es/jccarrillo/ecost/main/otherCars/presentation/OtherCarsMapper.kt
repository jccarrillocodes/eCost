package es.jccarrillo.ecost.main.otherCars.presentation

import es.jccarrillo.ecost.core.preferences.domain.GasReference
import es.jccarrillo.ecost.core.preferences.domain.GasReferences
import es.jccarrillo.ecost.core.preferences.domain.PreferencesData

fun PreferencesData.toOtherCarsState() = OtherCarsState(
    dieselPrice = this.gasReferences.diesel.toPriceCarField(),
    dieselQuantity = this.gasReferences.diesel.toQuantityCarField(),
    gasolinePrice = this.gasReferences.gasoline.toPriceCarField(),
    gasolineQuantity = this.gasReferences.gasoline.toQuantityCarField()
)

private fun GasReference.toPriceCarField() = CarField(
    value = this.pricePerLiter.toString(),
    state = FieldState.IDLE
)

private fun GasReference.toQuantityCarField() = CarField(
    value = this.litersPer100KM.toString(),
    state = FieldState.IDLE
)

fun OtherCarsState.toGasReferences() = GasReferences(
    diesel = toGasReference(dieselPrice, dieselQuantity),
    gasoline = toGasReference(gasolinePrice, gasolineQuantity)
)

private fun toGasReference(price: CarField, quantity: CarField) = GasReference(
    pricePerLiter = price.value.toDoubleOrNull()
        ?: throw Exception("Price per liter ${price.value} is not valid"),
    litersPer100KM = quantity.value.toDoubleOrNull()
        ?: throw Exception("Liters per 100km ${quantity.value} is not valid")
)