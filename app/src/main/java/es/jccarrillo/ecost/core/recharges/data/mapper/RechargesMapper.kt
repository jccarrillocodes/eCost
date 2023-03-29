package es.jccarrillo.ecost.core.recharges.data.mapper

import es.jccarrillo.ecost.core.recharges.data.local.entity.LocalRecharge
import es.jccarrillo.ecost.core.recharges.domain.model.Recharge

/* toDomain */

fun LocalRecharge.toDomain() = Recharge(
    timestampEpoch = timestampEpoch,
    price = price,
    quantity = quantity
)

/* toData */

fun Recharge.toData() = LocalRecharge(
    timestampEpoch = timestampEpoch,
    price = price,
    quantity = quantity
)