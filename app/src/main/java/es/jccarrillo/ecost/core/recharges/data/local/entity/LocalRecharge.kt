package es.jccarrillo.ecost.core.recharges.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalRecharge(
    @PrimaryKey
    val timestampEpoch: Long,
    val price: Double,
    val quantity: Double
)

