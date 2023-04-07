package es.jccarrillo.ecost.main.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.android.awaitFrame

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QuantityTextField(
    value: String,
    changeValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    textStyle: TextStyle = MaterialTheme.typography.h5,
    borderColor: Color = MaterialTheme.colors.primaryVariant,
    readOnly: Boolean = false,
    requestFocus: Boolean = true
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        )
    }

    if (textFieldValueState.text != value)
        textFieldValueState =
            textFieldValueState.copy(
                text = value,
                selection = TextRange(value.length)
            )

    Column(
        modifier = modifier
    ) {
        BasicTextField(
            value = textFieldValueState,
            onValueChange = {
                textFieldValueState = it
                changeValue(it.text)
            },
            readOnly = readOnly,
            textStyle = textStyle,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done, keyboardType = KeyboardType.Decimal
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawContent()
                    drawLine(
                        color = borderColor,
                        start = Offset(
                            x = 0f,
                            y = size.height - 1.dp.toPx(),
                        ),
                        end = Offset(
                            x = size.width,
                            y = size.height - 1.dp.toPx(),
                        ),
                        strokeWidth = 1.dp.toPx(),
                    )
                }
                .focusRequester(focusRequester)
        )

        if (label.isNotEmpty()) {
            Text(
                text = label,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    LaunchedEffect(focusRequester, requestFocus, readOnly) {
        if (requestFocus && !readOnly) {
            focusRequester.requestFocus()
            awaitFrame()
            keyboardController?.show()
        }
    }
}