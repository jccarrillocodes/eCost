package es.jccarrillo.ecost.main.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.jccarrillo.ecost.main.calculation.presentation.CalculateScreen
import es.jccarrillo.ecost.main.mycar.MyCarScreen
import es.jccarrillo.ecost.main.otherCars.presentation.OtherCarsScreen
import es.jccarrillo.ecost.main.recharges.presentation.RechargesScreen

@Composable
fun MainScreen() {
    val navController = rememberMainNavController()

    NavHost(navController = navController, startDestination = navController.main.name) {
        composable(navController.main.name) {
            CalculateScreen(
                openMyCar = {
                    navController.myCar.navigate()
                },
                openRechargeRegistries = {
                    navController.recharges.navigate()
                }
            )
        }

        composable(navController.myCar.name) {
            MyCarScreen(
                onBack = {
                    navController.popBackStack()
                },
                onOpenOtherCars = {
                    navController.otherCars.navigate()
                }
            )
        }

        composable(navController.otherCars.name) {
            OtherCarsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(navController.recharges.name) {
            RechargesScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}
