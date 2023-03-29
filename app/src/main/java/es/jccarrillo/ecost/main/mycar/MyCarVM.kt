package es.jccarrillo.ecost.main.mycar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jccarrillo.ecost.core.preferences.domain.PreferencesRepository
import es.jccarrillo.ecost.core.utils.toI18NDouble
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCarVM @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    val state = preferencesRepository.data
        .map { it.toMyCarState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyMyCarState()
        )

    fun onUpdateCarName(it: String) = viewModelScope.launch {
        preferencesRepository.set(preferencesRepository.data.first().copy(carName = it))
    }

    fun onUpdateCurrencyFormat(formatCurrency: String, formatConsumption: String) =
        viewModelScope.launch {
            preferencesRepository.set(
                preferencesRepository.data.first()
                    .copy(currencyFormat = formatCurrency, consumptionLabel = formatConsumption)
            )
        }

    fun onUpdateConsumption(kwh100km: String) = viewModelScope.launch {
        kwh100km.toI18NDouble()?.let {
            preferencesRepository.set(
                preferencesRepository.data.first()
                    .copy(kwPer100KM = it)
            )
        }
    }
}