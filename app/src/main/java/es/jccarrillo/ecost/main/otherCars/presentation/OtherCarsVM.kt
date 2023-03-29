package es.jccarrillo.ecost.main.otherCars.presentation

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jccarrillo.ecost.core.preferences.domain.GasReferences
import es.jccarrillo.ecost.core.preferences.domain.PreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class OtherCarsVM @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    var state by mutableStateOf(otherCarsState())
        private set

    init {
        viewModelScope.launch {
            state = preferencesRepository.data.first().toOtherCarsState()

            snapshotFlow { state }
                .mapLatest {
                    delay(1000)
                    runCatching {
                        if (it.waitingToSave()) {
                            it.toGasReferences()
                        } else {
                            null
                        }
                    }.getOrNull()
                }
                .filterNotNull()
                .onEach {
                    Log.d("OtherCarsVM", "Saving $it")
                    // Save
                    save(it)
                    // Reset saving
                    state = state.resetSaving()
                }
                .collect()
        }
    }

    private fun OtherCarsState.resetSaving() = OtherCarsState(
        dieselPrice = dieselPrice.resetSaving(),
        dieselQuantity = dieselQuantity.resetSaving(),
        gasolinePrice = gasolinePrice.resetSaving(),
        gasolineQuantity = gasolineQuantity.resetSaving()
    )

    private fun CarField.resetSaving() = CarField(
        value = value,
        state = if (state.isSaving()) FieldState.IDLE else state
    )

    private fun OtherCarsState.waitingToSave(): Boolean {
        return this.dieselPrice.waitingToSave() ||
                this.dieselQuantity.waitingToSave() ||
                this.gasolinePrice.waitingToSave() ||
                this.gasolineQuantity.waitingToSave()
    }

    private fun CarField.waitingToSave(): Boolean = this.state == FieldState.SAVING

    fun updateDieselPrice(price: String) {
        state = state.copy(dieselPrice = state.dieselPrice.updateValue(price))
    }

    fun updateDieselLitersPer100km(quantity: String) {
        state = state.copy(dieselQuantity = state.dieselQuantity.updateValue(quantity))
    }

    fun updateGasolinePrice(price: String) {
        state = state.copy(gasolinePrice = state.gasolinePrice.updateValue(price))
    }

    fun updateGasolineLitersPer100km(quantity: String) {
        state = state.copy(gasolineQuantity = state.gasolineQuantity.updateValue(quantity))
    }

    suspend fun willGoBack() {
        if (state.waitingToSave()) {
            snapshotFlow {
                state
            }.first {
                !it.waitingToSave()
            }
        }
    }

    private suspend fun save(gasReferences: GasReferences) {
        val original = preferencesRepository.data.first()
        preferencesRepository.set(original.copy(gasReferences = gasReferences))
    }

    private fun CarField.updateValue(value: String) = copy(
        value = value,
        state = if (value.toDoubleOrNull() == null) FieldState.ERROR else FieldState.SAVING
    )

}