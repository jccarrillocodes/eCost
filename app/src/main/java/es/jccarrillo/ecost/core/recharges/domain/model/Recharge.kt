package es.jccarrillo.ecost.core.recharges.domain.model

data class Recharge(
    val timestampEpoch: Long,
    val price: Double,
    val quantity: Double
)
