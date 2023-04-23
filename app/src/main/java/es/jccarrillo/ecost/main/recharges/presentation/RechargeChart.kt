package es.jccarrillo.ecost.main.recharges.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size


@Composable
internal fun RechargeChart(
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