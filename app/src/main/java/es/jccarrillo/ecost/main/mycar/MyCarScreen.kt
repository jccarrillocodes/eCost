package es.jccarrillo.ecost.main.mycar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
