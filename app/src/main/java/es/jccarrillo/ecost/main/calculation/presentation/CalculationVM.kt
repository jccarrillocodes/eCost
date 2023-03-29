package es.jccarrillo.ecost.main.calculation.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jccarrillo.ecost.core.preferences.domain.PreferencesRepository
import es.jccarrillo.ecost.core.utils.toI18NDouble
import es.jccarrillo.ecost.main.calculation.domain.usecase.CalculateResumeUseCase
import es.jccarrillo.ecost.main.calculation.domain.repository.LastValueRepository
import es.jccarrillo.ecost.main.calculation.domain.usecase.AddRechargeUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class CalculationVM @Inject constructor(
    private val calculateResumeUseCase: CalculateResumeUseCase,
    private val lastValueRepository: LastValueRepository,
    private val preferencesRepository: PreferencesRepository,
    private val addRechargeUseCase: AddRechargeUseCase,
) : ViewModel() {

    private var currencyFormat: String = ""

    var state by mutableStateOf(calculationState())
        private set

    init {
        viewModelScope.launch {
            currencyFormat = preferencesRepository.data.first().currencyFormat
            setPricePerKWh(lastValueRepository.get())
        }

        viewModelScope.launch {
            preferencesRepository.data.collect {
                state = state.copy(carName = it.carName, valueLabel = it.consumptionLabel)
                currencyFormat = it.currencyFormat
            }
        }
    }

    fun setPricePerKWh(value: String) = viewModelScope.launch {
        state = if (value.isBlank() || value.isEmpty()) {
            state.copy(value = "")
        } else {
            value.toI18NDouble()?.let { valueAsDouble ->

                val resume = calculateResumeUseCase(valueAsDouble)

                viewModelScope.launch {
                    lastValueRepository.set(value)
                }

                CalculationState(
                    carName = state.carName,
                    value = value,
                    valueLabel = state.valueLabel,
                    costPerKm = resume.costPerKm.toFormattedString(),
                    costPer100Km = resume.costPer100km.toFormattedString(),
                    costDiffDiesel = resume.costDifferenceWithDiesel.toCostDiff(valueAsDouble),
                    costDiffGasoline = resume.costDifferenceWithGasoline.toCostDiff(valueAsDouble)
                )
            } ?: state.copy(value = "")
        }
    }

    private fun Double.toFormattedString(): String =
        String.format(currencyFormat, this)

    private fun Double.toCostDiff(reference: Double): CostDiff =
        CostDiff(
            per100km = this.toFormattedString(),
            goodness = this.toDiffState(reference)
        )

    private fun Double.toDiffState(reference: Double): DiffState {
        return if (abs(reference - this) <= 0.5)
            DiffState.INDIFFERENT
        else if (reference > this)
            DiffState.BAD
        else
            DiffState.GOOD
    }

    suspend fun addRecharge(price: String, quantity: String): Boolean {
        if (price.isNumberValid() && quantity.isNumberValid()) {
            runCatching {
                val dPrice = price.toI18NDouble()
                val dQuantity = quantity.toI18NDouble()
                if (dPrice!= null && dQuantity != null) {
                    addRechargeUseCase(
                        price = dPrice,
                        quantity = dQuantity
                    )
                    return true
                }
            }
        }
        return false
    }

    private fun String.isNumberValid() =
        isNotBlank() && isNotEmpty() && matches(Regex("^\\d+[,|.]?\\d*$"))
}