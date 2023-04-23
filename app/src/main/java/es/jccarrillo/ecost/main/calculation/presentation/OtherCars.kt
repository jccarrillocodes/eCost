package es.jccarrillo.ecost.main.calculation.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.jccarrillo.ecost.R


@Composable
internal fun OtherCars(
    state: CalculationState,
) {

    Card(
        elevation = 2.dp,
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(horizontal = 10.dp)
        ) {
            ResumeCard(
                caption = stringResource(R.string.diesel),
                value = state.costDiffDiesel.per100km,
                diffState = state.costDiffDiesel.goodness,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            ResumeCard(
                caption = stringResource(R.string.gasoline),
                value = state.costDiffGasoline.per100km,
                diffState = state.costDiffGasoline.goodness,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}
