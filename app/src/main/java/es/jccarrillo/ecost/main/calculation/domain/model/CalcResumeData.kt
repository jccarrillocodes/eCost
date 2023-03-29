package es.jccarrillo.ecost.main.calculation.domain.model

data class CalcResumeData(
    val costPerKm: Double,
    val costPer100km: Double,
    val costDifferenceWithDiesel: Double,
    val costDifferenceWithGasoline: Double
)
