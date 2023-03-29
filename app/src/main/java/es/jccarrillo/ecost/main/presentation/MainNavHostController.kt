package es.jccarrillo.ecost.main.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator

class MainNavHostController(context: Context) : NavHostController(context) {

    val main = Destination("main", this)
    val myCar = Destination("myCar", this)
    val recharges = Destination("recharges", this)
    val otherCars = Destination("otherCars", this)

}

class Destination(val name: String, private val navHostController: MainNavHostController) {
    fun navigate() {
        navHostController.navigate(name)
    }
}

@Composable
fun rememberMainNavController(): MainNavHostController {
    val context = LocalContext.current
    return rememberSaveable(saver = mainNavControllerSaver(context)) {
        createMainNavController(context)
    }
}

private fun mainNavControllerSaver(
    context: Context
): Saver<MainNavHostController, *> = Saver(
    save = { it.saveState() },
    restore = { createMainNavController(context).apply { restoreState(it) } }
)

private fun createMainNavController(context: Context) =
    MainNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }