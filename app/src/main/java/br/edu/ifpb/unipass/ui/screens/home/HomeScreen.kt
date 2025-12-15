package br.edu.ifpb.unipass.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.edu.ifpb.unipass.models.QuickAccessOption
import br.edu.ifpb.unipass.models.Trip
import br.edu.ifpb.unipass.models.User
import br.edu.ifpb.unipass.navigation.Routes
import br.edu.ifpb.unipass.repository.TripRepository
import br.edu.ifpb.unipass.ui.components.NextTripCard
import br.edu.ifpb.unipass.ui.components.QuickAccessGrid
import br.edu.ifpb.unipass.ui.components.RealTimeMapSection
import br.edu.ifpb.unipass.ui.components.UserProfileHeader

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    val repository = remember { TripRepository() }

    val currentUser = User(
        name = "Maria",
        initials = "MS",
        notificationCount = 3
    )

    var nextTrip by remember { mutableStateOf<Trip?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Observar próxima viagem em tempo real
    DisposableEffect(Unit) {
        android.util.Log.d("HomeScreen", "=== Iniciando observação em tempo real ===")

        val listener = repository.observeNextTrip { trip ->
            nextTrip = trip
            isLoading = false
            android.util.Log.d("HomeScreen", "Dados atualizados: ${trip?.route ?: "nenhuma"}")
        }

        onDispose {
            android.util.Log.d("HomeScreen", "Removendo listener de tempo real")
            listener.remove()
        }
    }

    val quickAccessOptions = listOf(
        QuickAccessOption(
            icon = Icons.Filled.DateRange,
            label = "Reservar",
            onClick = { navController.navigate(Routes.RESERVA) }
        ),
        QuickAccessOption(
            icon = Icons.Filled.AccessTime,
            label = "Horários",
            onClick = { }
        ),
        QuickAccessOption(
            icon = Icons.AutoMirrored.Filled.List,
            label = "Histórico",
            onClick = { }
        ),
        QuickAccessOption(
            icon = Icons.Filled.HeadsetMic,
            label = "Suporte",
            onClick = { }
        )
    )

    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        HomeContent(
            modifier = modifier,
            user = currentUser,
            trip = nextTrip,
            quickAccessOptions = quickAccessOptions,
            onNotificationClick = { },
            onViewTripDetails = { },
            onViewMap = { },
            onCancelReservation = { },
            onViewFullMap = { }
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    user: User,
    trip: Trip?,
    quickAccessOptions: List<QuickAccessOption>,
    onNotificationClick: () -> Unit,
    onViewTripDetails: () -> Unit,
    onViewMap: () -> Unit,
    onCancelReservation: () -> Unit,
    onViewFullMap: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        UserProfileHeader(
            user = user,
            onNotificationClick = onNotificationClick
        )

        QuickAccessGrid(options = quickAccessOptions)

        NextTripCard(
            trip = trip,
            onViewMap = onViewMap,
            onCancelReservation = onCancelReservation,
            onViewDetails = onViewTripDetails
        )

        RealTimeMapSection(onViewFullMap = onViewFullMap)
    }
}

