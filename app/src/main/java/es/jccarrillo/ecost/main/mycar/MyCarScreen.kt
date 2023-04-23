package es.jccarrillo.ecost.main.mycar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.jccarrillo.ecost.R
import es.jccarrillo.ecost.main.presentation.components.QuantityTextField

@Composable
fun MyCarScreen(vm: MyCarVM = hiltViewModel(), onBack: () -> Unit, onOpenOtherCars: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.car_configuration))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val state = vm.state.collectAsState()

        MyCarContent(
            paddingValues = paddingValues,
            state = state.value,
            onUpdateCarName = vm::onUpdateCarName,
            onUpdateCurrencyFormat = vm::onUpdateCurrencyFormat,
            onUpdateConsumption = vm::onUpdateConsumption,
            onOpenOtherCars = onOpenOtherCars
        )
    }
}

@Composable
private fun MyCarContent(
    paddingValues: PaddingValues,
    state: MyCarState,
    onUpdateCarName: (String) -> Unit,
    onUpdateCurrencyFormat: (String, String) -> Unit,
    onUpdateConsumption: (String) -> Unit,
    onOpenOtherCars: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(
                state = rememberScrollState()
            )
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = onUpdateCarName,
            label = {
                Text(stringResource(R.string.my_car_name), style = MaterialTheme.typography.h5)
            },
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier.fillMaxWidth()
        )

        FormatsDropdown(
            formatPrice = state.formatPrice,
            consumptionLabel = state.consumptionLabel,
            onUpdateCurrencyFormat = onUpdateCurrencyFormat
        )

        QuantityTextField(
            value = state.kwPer100km,
            label = stringResource(R.string.consumption_per_100km),
            changeValue = {
                onUpdateConsumption(it)
            }
        )

        OutlinedButton(
            onClick = onOpenOtherCars,
            modifier = Modifier.fillMaxHeight()
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(id = R.string.configure)
            )
            Text(text = stringResource(R.string.other_cars))
        }
    }
}

@Composable
private fun FormatsDropdown(
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