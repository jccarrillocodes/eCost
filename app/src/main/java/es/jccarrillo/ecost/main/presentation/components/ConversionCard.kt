package es.jccarrillo.ecost.main.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.jccarrillo.ecost.main.presentation.QuantityTextField

@Composable
fun ConversionCard(
    carName: String,
    value: String,
    changeValue: (String) -> Unit,
    label: String,
    convertedValue: String,
    requestFocus: Boolean = true,
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .padding(
                    horizontal = 10.dp,
                    vertical = 20.dp
                )
        ) {
            Text(
                text = carName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                QuantityTextField(
                    value = value,
                    label = label,
                    changeValue = {
                        changeValue(it)
                    },
                    requestFocus = requestFocus,
                    textStyle = MaterialTheme.typography.h2,
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .padding(horizontal = 10.dp)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "As",
                    modifier = Modifier.padding(vertical = 30.dp)
                )
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                ) {
                    Text(
                        text = convertedValue,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .height(1.dp)
                    )
                    Text(
                        text = "€/100km",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}