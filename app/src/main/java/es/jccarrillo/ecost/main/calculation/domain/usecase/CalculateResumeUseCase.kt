package es.jccarrillo.ecost.main.calculation.domain.usecase

import es.jccarrillo.ecost.core.preferences.domain.GasReference
import es.jccarrillo.ecost.core.preferences.domain.PreferencesRepository
import es.jccarrillo.ecost.main.calculation.domain.model.CalcResumeData
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CalculateResumeUseCase @Inject constructor(
    private val preferencesRepo: PreferencesRepository
) {
    suspend operator fun invoke(eurPerKWh: Double): CalcResumeData {
        val prefs = preferencesRepo.data.first()

        val oneHundred = prefs.kwPer100KM * eurPerKWh

        return CalcResumeData(
            costPerKm = oneHundred / 100.0,
            costPer100km = oneHundred,
            costDifferenceWithDiesel = prefs.gasReferences.diesel
                .priceDifferencePer100KM(oneHundred),
            costDifferenceWithGasoline = prefs.gasReferences.gasoline
                .priceDifferencePer100KM(oneHundred)
        )
    }

    private fun GasReference.priceDifferencePer100KM(electricCostPer100km: Double): Double {
        return (pricePerLiter * litersPer100KM) - electricCostPer100km
    }
}