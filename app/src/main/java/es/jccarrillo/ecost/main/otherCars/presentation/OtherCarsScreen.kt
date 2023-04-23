package es.jccarrillo.ecost.main.otherCars.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.jccarrillo.ecost.R
import es.jccarrillo.ecost.core.presentation.theme.RedComplementary
import es.jccarrillo.ecost.core.presentation.theme.RedDarkComplementary
import es.jccarrillo.ecost.main.presentation.components.QuantityTextField
import kotlinx.coroutines.launch

@Composable
fun OtherCarsScreen(
    vm: OtherCarsVM = hiltViewModel(), onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(R.string.other_cars_configuration))
        }, navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    vm.willGoBack()
                    onBack()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.back)
                )
            }
        })
    }) { paddingValues ->
        OtherCarsContent(
            paddingValues = paddingValues,
            state = vm.state,
            updateDieselPrice = vm::updateDieselPrice,
            updateDieselLitersPer100km = vm::updateDieselLitersPer100km,
            updateGasolinePrice = vm::updateGasolinePrice,
            updateGasolineLitersPer100km = vm::updateGasolineLitersPer100km,
        )
    }
}

@Composable
private fun OtherCarsContent(
    paddingValues: PaddingValues,
    state: OtherCarsState,
    updateDieselPrice: (String) -> Unit,
    updateDieselLitersPer100km: (String) -> Unit,
    updateGasolinePrice: (String) -> Unit,
    updateGasolineLitersPer100km: (String) -> Unit,
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
        CarFields(
            name = stringResource(id = R.string.diesel),
            pricePerLiter = state.dieselPrice.value,
            pricePerLiterError = state.dieselPrice.state.isError(),
            litersPer100km = state.dieselQuantity.value,
            literPer100kmError = state.dieselQuantity.state.isError(),
            onPricePerLiterChange = updateDieselPrice,
            onLitersPer100kmChange = updateDieselLitersPer100km
        )

        CarFields(
            name = stringResource(id = R.string.gasoline),
            pricePerLiter = state.gasolinePrice.value,
            pricePerLiterError = state.gasolinePrice.state.isError(),
            litersPer100km = state.gasolineQuantity.value,
            literPer100kmError = state.gasolineQuantity.state.isError(),
            onPricePerLiterChange = updateGasolinePrice,
            onLitersPer100kmChange = updateGasolineLitersPer100km
        )
    }
}

@Composable
private fun CarFields(
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