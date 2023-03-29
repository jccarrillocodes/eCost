package es.jccarrillo.ecost.core.recharges.domain.repository

import es.jccarrillo.ecost.core.recharges.domain.model.Recharge
import es.jccarrillo.ecost.core.recharges.domain.model.Recharges
import kotlinx.coroutines.flow.Flow

interface RechargeRepository {
    suspend fun data(minTimestampEpoch: Long = Long.MIN_VALUE): Recharges

    suspend fun insert(recharge: Recharge)

    suspend fun removeAll()

    suspend fun remove(timestampEpoch: Long)
}