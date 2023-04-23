package es.jccarrillo.ecost.main.calculation.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.jccarrillo.ecost.R
import es.jccarrillo.ecost.main.presentation.components.ConversionCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalculateScreen(
    vm: CalculationVM = hiltViewModel(),
    openMyCar: () -> Unit,
    openRechargeRegistries: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )


    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            AddRechargeView(
                value = vm.state.value,
                priceFormat = vm.state.valueLabel,
                focus = modalSheetState.isVisible,
                addRecharge = { price, quantity ->
                    coroutineScope.launch {
                        val success = vm.addRecharge(price, quantity)

                        if (success) {
                            modalSheetState.hide()
                        }
                    }
                }
            )
        }
    ) {
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.ecost_calculator))
                    }
                )
            },

            ) { paddingValues ->
            CalculateContent(
                state = vm.state,
                paddingValues = paddingValues,
                inputFocused = modalSheetState.isVisible.not(),
                setPricePerKWh = vm::setPricePerKWh,
                openMyCar = openMyCar,
                openRechargeRegistries = openRechargeRegistries,
                addRechargeClick = {
                    coroutineScope.launch {
                        modalSheetState.show()

                    }
                }
            )
        }
    }
}

@Composable
fun CalculateContent(
    state: CalculationState,
    paddingValues: PaddingValues = PaddingValues(),
    inputFocused: Boolean,
    setPricePerKWh: (String) -> Unit,
    openMyCar: () -> Unit = {},
    openRechargeRegistries: () -> Unit = {},
    addRechargeClick: (Double) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(16.dp)
            .padding(paddingValues)
            .verticalScroll(
                state = rememberScrollState()
            )
    ) {
        val configuration = LocalConfiguration.current

        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        ConversionCard(
                            carName = state.carName,
                            value = state.value,
                            label = state.valueLabel,
                            changeValue = { setPricePerKWh(it) },
                            requestFocus = inputFocused,
                            convertedValue = state.costPer100Km
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        ComparisonCards(
                            openMyCar = openMyCar,
                            openRechargeRegistries = openRechargeRegistries,
                            state = state,
                            addRechargeClick = addRechargeClick
                        )
                    }

                }
            }
            else -> {
                ConversionCard(
                    carName = state.carName,
                    value = state.value,
                    label = state.valueLabel,
                    changeValue = { setPricePerKWh(it) },
                    requestFocus = inputFocused,
                    convertedValue = state.costPer100Km
                )

                ComparisonCards(
                    state = state,
                    openMyCar = openMyCar,
                    openRechargeRegistries = openRechargeRegistries,
                    addRechargeClick = addRechargeClick
                )
            }
        }


    }

}

@Composable
private fun ComparisonCards(
    openMyCar: () -> Unit,
    openRechargeRegistries: () -> Unit,
    state: CalculationState,
    addRechargeClick: (Double) -> Unit
) {

    MyCarActions(
        openMyCar = openMyCar,
        openRechargeRegistries = openRechargeRegistries,
        state = state,
        addRechargeClick = addRechargeClick
    )

    OtherCars(
        state = state,
    )

}
