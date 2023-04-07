package es.jccarrillo.ecost.main.calculation.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.jccarrillo.ecost.R
import es.jccarrillo.ecost.core.presentation.theme.GreenComplementary
import es.jccarrillo.ecost.core.presentation.theme.RedComplementary
import es.jccarrillo.ecost.core.presentation.util.ifTrue
import es.jccarrillo.ecost.core.utils.toI18NDouble
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
                        Text(text = "eCost calculator")
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

@Composable
private fun MyCarActions(
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
                    contentDescription = "Add recharge"
                )
                Text(text = "Add recharge")
            }
        }
        IconButton(onClick = openRechargeRegistries) {
            Icon(
                painter = painterResource(id = R.drawable.bar_chart_24),
                contentDescription = "Registries"
            )

        }
        IconButton(onClick = openMyCar) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Configure"
            )
        }
    }

}

@Composable
private fun OtherCars(
    state: CalculationState,
) {

    Card(
        elevation = 4.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(10.dp)
        ) {
            ResumeCard(
                caption = "Diesel",
                value = state.costDiffDiesel.per100km,
                diffState = state.costDiffDiesel.goodness,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            ResumeCard(
                caption = "Gasoline",
                value = state.costDiffGasoline.per100km,
                diffState = state.costDiffGasoline.goodness,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}

private fun DiffState.toBackgroundColor(): Color? = when (this) {
    DiffState.GOOD -> GreenComplementary
    DiffState.BAD -> RedComplementary
    DiffState.INDIFFERENT -> null
}

@Composable
private fun ResumeCard(
    modifier: Modifier = Modifier,
    caption: String,
    value: String,
    diffState: DiffState
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(8.dp, 16.dp)
    ) {
        val (icon, desc) = when (diffState) {
            DiffState.GOOD -> Icons.Default.KeyboardArrowUp to "Good"
            DiffState.BAD -> Icons.Default.KeyboardArrowDown to "Bad"
            DiffState.INDIFFERENT -> Icons.Default.Done to "Not bad"
        }
        val tint = diffState.toBackgroundColor() ?: LocalContentColor.current
        ContentColorComponent(contentColor = tint) {
            Icon(imageVector = icon, contentDescription = desc)

            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(IntrinsicSize.Min)
            ) {
                Text(
                    text = caption,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = value, style = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ContentColorComponent(
    contentColor: Color = LocalContentColor.current,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        content = content
    )
}