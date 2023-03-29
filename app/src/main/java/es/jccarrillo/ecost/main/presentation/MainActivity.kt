package es.jccarrillo.ecost.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import es.jccarrillo.ecost.core.presentation.theme.ECostTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ECostTheme {
                MainScreen()
            }
        }
    }
}
