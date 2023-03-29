package es.jccarrillo.ecost.main.recharges.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jccarrillo.ecost.core.recharges.domain.model.Recharge
import es.jccarrillo.ecost.core.recharges.domain.model.Recharges
import es.jccarrillo.ecost.core.recharges.domain.repository.RechargeRepository
import kotlinx.coroutines.launch
import java.lang.Double.max
import java.lang.Double.min
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class RechargesVM @Inject constructor(
    private val rechargeRepository: RechargeRepository
) : ViewModel() {

    var state by mutableStateOf<RechargesState>(RechargesState.Loading)
        private set

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        state = RechargesState.Loading
        rechargeRepository.data().let { recharges ->
            state = if (recharges.items.isEmpty()) {
                RechargesState.Empty
            } else {
                recharges.items.toDisplayData()
            }
        }
    }

    private fun List<Recharge>.toDisplayData(): RechargesState.DisplayData {
        val maxPrice = max(1.0, maxOf { it.price })
        val maxQuantity = max(1.0, maxOf { it.quantity })

        return RechargesState.DisplayData(
            items = map {
                RechargeUI(
                    date = it.timestampEpoch.toStringDate(),
                    price = it.price / maxPrice,
                    quantity = it.quantity / maxQuantity
                )
            }
        )
    }

    private fun Long.toStringDate(): String {
        return Instant.ofEpochSecond(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
            .toString()
            .replace("T", "\n")
    }

    fun clear() = viewModelScope.launch {
        rechargeRepository.removeAll()
        state = RechargesState.Empty
    }

    fun remove(index: Int) {
        viewModelScope.launch {
            getByIndex(index)?.also {
                rechargeRepository.remove(it.timestampEpoch)
                loadData()
            }
        }
    }

    private suspend fun getByIndex(index: Int): Recharge? = runCatching {
        rechargeRepository.data().items[index]
    }.getOrNull()

}


