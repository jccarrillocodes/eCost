package es.jccarrillo.ecost.main.calculation.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.jccarrillo.ecost.R
import es.jccarrillo.ecost.main.presentation.components.QuantityTextField

@Composable
fun AddRechargeView(
    value: String,
    priceFormat: String,
    focus: Boolean,
    addRecharge: (String, String) -> Unit
) {
    val recharge = remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = stringResource(R.string.add_recharge), style = MaterialTheme.typography.h4)
        QuantityTextField(
            value = value,
            label = priceFormat,
            changeValue = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        QuantityTextField(
            value = recharge.value,
            label = stringResource(R.string.kw),
            changeValue = { recharge.value = it },
            requestFocus = focus,
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = { addRecharge(value, recharge.value) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.add))
        }
    }
}