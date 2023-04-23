package es.jccarrillo.ecost.main.recharges.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.jccarrillo.ecost.R
import kotlin.math.min


@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun RechargeRow(
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
