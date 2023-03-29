package es.jccarrillo.ecost.main.recharges.presentation

sealed class RechargesState {
    object Loading : RechargesState()
    object Empty : RechargesState()
    class DisplayData(val items: List<RechargeUI>) : RechargesState()
}

data class RechargeUI(
    val date: String,
    val price: Double,
    val quantity: Double
)