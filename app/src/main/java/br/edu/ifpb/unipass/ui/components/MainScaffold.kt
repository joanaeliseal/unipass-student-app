package br.edu.ifpb.unipass.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.edu.ifpb.unipass.models.BottomNavItem
import br.edu.ifpb.unipass.navigation.Routes

@Composable
fun MainScaffold(
    navController: NavController,
    content: @Composable (Modifier) -> Unit
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            route = Routes.HOME,
            label = "Início",
            icon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home
        ),
        BottomNavItem(
            route = Routes.VIAGENS,
            label = "Viagens",
            icon = Icons.Outlined.DirectionsBus,
            selectedIcon = Icons.Filled.DirectionsBus
        ),
        BottomNavItem(
            route = Routes.CARTEIRINHA,
            label = "Carteirinha",
            icon = Icons.Outlined.CreditCard,
            selectedIcon = Icons.Filled.CreditCard
        ),
        BottomNavItem(
            route = Routes.PERFIL,
            label = "Perfil",
            icon = Icons.Outlined.Person,
            selectedIcon = Icons.Filled.Person
        )
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                items = bottomNavItems
            )
        }
    ) { paddingValues ->
        content(Modifier.padding(paddingValues))
    }
}
