package br.edu.ifpb.unipass.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifpb.unipass.ui.screens.login.LoginScreen
import br.edu.ifpb.unipass.ui.screens.home.HomeScreen
import br.edu.ifpb.unipass.ui.screens.reserva.ReservaViagemScreen
import br.edu.ifpb.unipass.ui.screens.carteirinha.CarteirinhaScreen



@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(Routes.RESERVA) {
            ReservaViagemScreen(navController)
        }

        composable(Routes.CARTEIRINHA) {
            CarteirinhaScreen(navController)
        }
    }
}
