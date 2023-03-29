package es.jccarrillo.ecost.main.calculation.presentation

data class CalculationState(
    val carName: String,
    val value: String,
    val valueLabel: String,
    val costPerKm: String,
    val costPer100Km: String,
    val costDiffDiesel: CostDiff,
    val costDiffGasoline: CostDiff
)

data class CostDiff(
    val per100km: String,
    val goodness: DiffState
)

enum class DiffState {
    GOOD, BAD, INDIFFERENT
}

fun calculationState() = CalculationState(
    carName = "My Car",
    value = "0.",
    valueLabel = "",
    costPerKm = "",
    costPer100Km = "",
    costDiffDiesel = CostDiff("", DiffState.INDIFFERENT),
    costDiffGasoline = CostDiff("", DiffState.INDIFFERENT),
)
