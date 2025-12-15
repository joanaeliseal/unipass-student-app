package br.edu.ifpb.unipass.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifpb.unipass.ui.components.MainScaffold
import br.edu.ifpb.unipass.ui.screens.studentCard.CarteirinhaScreen
import br.edu.ifpb.unipass.ui.screens.home.HomeScreen
import br.edu.ifpb.unipass.ui.screens.login.LoginScreen
import br.edu.ifpb.unipass.ui.screens.profile.PerfilScreen
import br.edu.ifpb.unipass.ui.screens.booking.ReservaViagemScreen
import br.edu.ifpb.unipass.ui.screens.trips.ViagensScreen

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
            MainScaffold(navController) { modifier ->
                HomeScreen(navController)
            }
        }

        composable(Routes.VIAGENS) {
            MainScaffold(navController) { modifier ->
                ViagensScreen(navController)
            }
        }

        composable(Routes.CARTEIRINHA) {
            MainScaffold(navController) { modifier ->
                CarteirinhaScreen(navController)
            }
        }

        composable(Routes.PERFIL) {
            MainScaffold(navController) { modifier ->
                PerfilScreen(navController)
            }
        }

        composable(Routes.RESERVA) {
            ReservaViagemScreen(navController)
        }
    }
}
