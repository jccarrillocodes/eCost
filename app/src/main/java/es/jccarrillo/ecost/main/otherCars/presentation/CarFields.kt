package es.jccarrillo.ecost.main.otherCars.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.jccarrillo.ecost.R
import es.jccarrillo.ecost.core.presentation.theme.RedComplementary
import es.jccarrillo.ecost.core.presentation.theme.RedDarkComplementary
import es.jccarrillo.ecost.main.presentation.components.QuantityTextField


@Composable
internal fun CarFields(
    name: String,
    pricePerLiter: String,
    pricePerLiterError: Boolean,
    litersPer100km: String,
    literPer100kmError: Boolean,
    onPricePerLiterChange: (String) -> Unit,
    onLitersPer100kmChange: (String) -> Unit
) {
    Text(text = name, style = MaterialTheme.typography.h5)
    Column {
        QuantityTextField(
            value = pricePerLiter, label = stringResource(R.string.price_per_liter), changeValue = onPricePerLiterChange
        )

        if (pricePerLiterError) {
            Error()
        }
    }
    Column {
        QuantityTextField(
            value = litersPer100km, label = stringResource(R.string.liters_100km), changeValue = onLitersPer100kmChange
        )

        if (literPer100kmError) {
            Error()
        }
    }
}

@Composable
private fun Error() {
    Text(
        text = stringResource(R.string.error_input_value),
        style = MaterialTheme.typography.body2.copy(color = RedDarkComplementary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(RedComplementary)
            .padding(4.dp)
    )

}