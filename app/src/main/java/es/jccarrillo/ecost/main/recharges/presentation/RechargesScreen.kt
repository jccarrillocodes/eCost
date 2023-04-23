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
