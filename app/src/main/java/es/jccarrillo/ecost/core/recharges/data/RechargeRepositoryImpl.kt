package es.jccarrillo.ecost.core.recharges.data

import es.jccarrillo.ecost.core.recharges.data.local.LocalRechargeDao
import es.jccarrillo.ecost.core.recharges.data.mapper.toData
import es.jccarrillo.ecost.core.recharges.data.mapper.toDomain
import es.jccarrillo.ecost.core.recharges.domain.model.Recharge
import es.jccarrillo.ecost.core.recharges.domain.model.Recharges
import es.jccarrillo.ecost.core.recharges.domain.repository.RechargeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RechargeRepositoryImpl @Inject constructor(private val localRechargeDao: LocalRechargeDao) :
    RechargeRepository {

    override suspend fun data(minTimestampEpoch: Long): Recharges {
        return withContext(Dispatchers.IO) {
            Recharges(
                items = localRechargeDao.getAllSince(minTimestampEpoch).map {
                    it.toDomain()
                }
            )
        }
    }

    override suspend fun insert(recharge: Recharge) {
        withContext(Dispatchers.IO) {
            localRechargeDao.insert(recharge.toData())
        }
    }

    override suspend fun removeAll() {
        withContext(Dispatchers.IO) {
            localRechargeDao.removeAll()
        }
    }

    override suspend fun remove(timestampEpoch: Long) {
        withContext(Dispatchers.IO){
            localRechargeDao.remove(timestampEpoch)
        }
    }


}