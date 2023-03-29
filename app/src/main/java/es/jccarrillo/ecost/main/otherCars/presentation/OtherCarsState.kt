package es.jccarrillo.ecost.main.otherCars.presentation

data class OtherCarsState(
    val dieselPrice: CarField,
    val dieselQuantity: CarField,
    val gasolinePrice: CarField,
    val gasolineQuantity: CarField
)

fun otherCarsState() = OtherCarsState(
    dieselPrice = carField(),
    dieselQuantity = carField(),
    gasolinePrice = carField(),
    gasolineQuantity = carField()
)

data class CarField(
    val value: String,
    val state: FieldState
)

fun carField() = CarField(value = "", state = FieldState.LOADING)

enum class FieldState {
    LOADING, IDLE, SAVING, ERROR;

    fun isError(): Boolean = this == ERROR
    fun isSaving(): Boolean = this == SAVING
}