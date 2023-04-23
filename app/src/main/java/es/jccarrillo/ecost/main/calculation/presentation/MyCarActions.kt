package es.jccarrillo.ecost.main.calculation.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import es.jccarrillo.ecost.R
import es.jccarrillo.ecost.core.utils.toI18NDouble


@Composable
internal fun MyCarActions(
    openMyCar: () -> Unit,
    openRechargeRegistries: () -> Unit,
    state: CalculationState,
    addRechargeClick: (Double) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        OutlinedButton(
            onClick = { state.value.toI18NDouble()?.let { addRechargeClick(it) } },
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ev_station_24),
                    contentDescription = stringResource(id = R.string.add_recharge)
                )
                Text(text = stringResource(id = R.string.add_recharge))
            }
        }
        IconButton(onClick = openRechargeRegistries) {
            Icon(
                painter = painterResource(id = R.drawable.bar_chart_24),
                contentDescription = stringResource(R.string.registries)
            )

        }
        IconButton(onClick = openMyCar) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(R.string.configure)
            )
        }
    }

}
