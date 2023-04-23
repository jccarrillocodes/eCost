package es.jccarrillo.ecost.main.recharges.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.jccarrillo.ecost.R
import java.util.*
import kotlin.math.min

@Composable
fun RechargesScreen(vm: RechargesVM = hiltViewModel(), onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Recharges")
        },
            actions = {
                if (vm.state is RechargesState.DisplayData) {
                    IconButton(onClick = { vm.clear() }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.clear_registry)
                        )
                    }
                }
            }, navigationIcon = {
                IconButton(onClick = {
                    onBack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                            id = R.string.back
                        )
                    )
                }
            })
    }) { paddingValues ->
        RechargesContent(
            paddingValues = paddingValues,
            state = vm.state,
            onRemove = vm::remove
        )
    }
}

@Composable
fun RechargesContent(paddingValues: PaddingValues, state: RechargesState, onRemove: (Int) -> Unit) {
    when (state) {
        is RechargesState.DisplayData -> DisplayData(
            paddingValues = paddingValues,
            rechargesState = state, onRemove = onRemove
        )

        RechargesState.Loading -> Loading()
        RechargesState.Empty -> Empty()
    }
}

@Composable
internal fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
internal fun Empty() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(R.string.empty_data))
    }
}

@Composable
internal fun DisplayData(
    paddingValues: PaddingValues,
    rechargesState: RechargesState.DisplayData,
    onRemove: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
    ) {

        itemsIndexed(rechargesState.items) { index, item ->
            RechargeRow(
                date = item.date,
                price = item.price,
                quantity = item.quantity,
                modifier = Modifier,
                onRemove = {
                    onRemove(index)
                }
            )
        }
    }
}

enum class SwipeLeftRight {
    INITIAL, LEFT
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RechargeRow(
    modifier: Modifier = Modifier,
    date: String,
    price: Double,
    quantity: Double,
    onRemove: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val swipeableState = rememberSwipeableState(
        initialValue = SwipeLeftRight.INITIAL,
        animationSpec = tween(durationMillis = 1000),
        confirmStateChange = {
            if (it == SwipeLeftRight.LEFT) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onRemove()
            }
            it == SwipeLeftRight.INITIAL
        }
    )
    Box {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 10.dp)
                .graphicsLayer {
                    alpha = min(1f, swipeableState.offset.value / -100)
                }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .swipeable(
                    state = swipeableState,
                    anchors = mapOf(
                        -200f to SwipeLeftRight.LEFT,
                        0f to SwipeLeftRight.INITIAL
                    ),
                    orientation = Orientation.Horizontal
                )
                .graphicsLayer {
                    translationX = swipeableState.offset.value
                }
        ) {
            Text(
                text = date,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .width(120.dp)
                    .padding(8.dp, 0.dp)
            )
            RechargeChart(
                price = price,
                quantity = quantity,
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
fun RechargeChart(
    modifier: Modifier = Modifier,
    offset: Offset = Offset(x = 0.0f, y = 0.5f),
    price: Double,
    quantity: Double
) {
    val animatedFactor = remember {
        Animatable(0f)
    }

    val quantityColor = MaterialTheme.colors.primary
    val priceColor = MaterialTheme.colors.secondary

    Box(modifier = modifier
        .drawBehind {
            drawRect(
                quantityColor,
                Offset(this.size.width * offset.x, this.size.height * offset.y),
                Size(
                    width = ((this.size.width * (1f - offset.x)) * quantity * animatedFactor.value).toFloat(),
                    height = this.size.height * (1f - offset.y)
                )
            )

            drawRect(
                priceColor,
                Offset(0f, 0f),
                Size(
                    width = ((this.size.width * (1f - offset.x)) * price * animatedFactor.value).toFloat(),
                    height = this.size.height * (1f - offset.y)
                )
            )

        }
    ) {
        // Nothing
    }

    LaunchedEffect(key1 = Unit) {
        animatedFactor.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000
            )
        )
    }

}