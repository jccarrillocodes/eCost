package es.jccarrillo.ecost.core.presentation.util

import androidx.compose.ui.Modifier

fun Modifier.ifTrue(condition: Boolean, exec: (Modifier) -> Modifier): Modifier =
    if (condition)
        exec(this)
    else
        this
