package es.jccarrillo.ecost.main.calculation.domain.usecase

import es.jccarrillo.ecost.core.recharges.domain.model.Recharge
import es.jccarrillo.ecost.core.recharges.domain.repository.RechargeRepository
import java.time.Instant
import javax.inject.Inject

class AddRechargeUseCase @Inject constructor(
    private val rechargeRepository: RechargeRepository
) {

    suspend operator fun invoke(price: Double, quantity: Double) {
        rechargeRepository.insert(
            Recharge(
                timestampEpoch = Instant.now().epochSecond,
                price = price,
                quantity = quantity
            )
        )
    }
}