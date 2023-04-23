package es.jccarrillo.ecost.main.calculation.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.jccarrillo.ecost.R
import es.jccarrillo.ecost.core.presentation.theme.GreenComplementary
import es.jccarrillo.ecost.core.presentation.theme.RedComplementary


@Composable
internal fun ResumeCard(
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
            DiffState.GOOD -> Icons.Default.KeyboardArrowUp to stringResource(R.string.good)
            DiffState.BAD -> Icons.Default.KeyboardArrowDown to stringResource(R.string.bad)
            DiffState.INDIFFERENT -> Icons.Default.Done to stringResource(R.string.not_bad)
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

private fun DiffState.toBackgroundColor(): Color? = when (this) {
    DiffState.GOOD -> GreenComplementary
    DiffState.BAD -> RedComplementary
    DiffState.INDIFFERENT -> null
}
