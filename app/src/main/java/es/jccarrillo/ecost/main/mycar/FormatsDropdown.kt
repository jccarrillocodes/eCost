package es.jccarrillo.ecost.main.mycar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.jccarrillo.ecost.R


@Composable
internal fun FormatsDropdown(
    formatPrice: String,
    consumptionLabel: String,
    onUpdateCurrencyFormat: (String, String) -> Unit
) {
    var dropdownExpanded by remember {
        mutableStateOf(false)
    }

    Column {
        Text(stringResource(R.string.currency_format), style = MaterialTheme.typography.h5)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dropdownExpanded = !dropdownExpanded }
                .padding(8.dp, 4.dp)) {
            Text(
                text = "$formatPrice - $consumptionLabel",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(R.string.see_options)
            )
        }
        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false }) {
            val formatOptions = listOf(
                ("%.3f€" to "€/kWh"),
                ("$%.3f" to "$/kWh")
            )
            formatOptions.forEach { option ->
                DropdownMenuItem(onClick = {
                    dropdownExpanded = false
                    onUpdateCurrencyFormat(option.first, option.second)
                }) {
                    Text(text = "${option.first} - ${option.second}")
                }
            }
        }
    }
}