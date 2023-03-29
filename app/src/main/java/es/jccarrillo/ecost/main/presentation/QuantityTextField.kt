package es.jccarrillo.ecost.main.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.android.awaitFrame

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QuantityTextField(
    value: String,
    label: String,
    changeValue: (String) -> Unit,
    readOnly: Boolean = false,
    requestFocus: Boolean = false
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

    OutlinedTextField(
        value = textFieldValueState,
        readOnly = readOnly,
        onValueChange = {
            textFieldValueState = it
            changeValue(it.text)
        },
        label = { Text(label, style = MaterialTheme.typography.h5) },
        textStyle = MaterialTheme.typography.h5,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Decimal
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        trailingIcon = {
            if (value.isNotEmpty() && !readOnly) {
                IconButton(onClick = {
                    textFieldValueState = textFieldValueState.copy(text = "")
                    changeValue("")
                }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
    )

    LaunchedEffect(focusRequester, requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
            awaitFrame()
            keyboardController?.show()
        }
    }
}