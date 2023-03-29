package es.jccarrillo.ecost.main.presentation

import androidx.compose.runtime.Composable

@Composable
fun PriceTextField(
    value: String,
    label: String,
    changeValue: (String) -> Unit,
    readOnly: Boolean = false,
    requestFocus: Boolean = false
) {
    QuantityTextField(
        value = value,
        label = label,
        changeValue = changeValue,
        readOnly = readOnly,
        requestFocus = requestFocus
    )
}