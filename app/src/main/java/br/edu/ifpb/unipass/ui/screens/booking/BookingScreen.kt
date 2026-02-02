package br.edu.ifpb.unipass.ui.screens.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.ifpb.unipass.ui.components.AppTopBar
import br.edu.ifpb.unipass.ui.components.EmptyState

data class AvailableTrip(
    val id: String,
    val date: String,
    val time: String,
    val origin: String,
    val destination: String,
    val availableSeats: Int,
    val totalSeats: Int
)

@Composable
fun ReservaViagemScreen(navController: NavController) {
    BookingScreen(navController = navController)
}

@Composable
fun BookingScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var selectedTripId by remember { mutableStateOf<String?>(null) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    // Dados de exemplo - TODO: Integrar com banco de dados
    val availableTrips = listOf(
        AvailableTrip("1", "03/02/2026", "06:30", "Sapé", "IFPB - Campus João Pessoa", 15, 40),
        AvailableTrip("2", "03/02/2026", "07:00", "Sapé", "UFPB - Campus I", 8, 40),
        AvailableTrip("3", "04/02/2026", "06:30", "Sapé", "IFPB - Campus João Pessoa", 22, 40),
        AvailableTrip("4", "04/02/2026", "07:00", "Sapé", "UFPB - Campus I", 18, 40),
        AvailableTrip("5", "05/02/2026", "06:30", "Sapé", "IFPB - Campus João Pessoa", 30, 40),
        AvailableTrip("6", "05/02/2026", "07:00", "Sapé", "UFPB - Campus I", 25, 40)
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = "Reservar Viagem",
                showNavigationIcon = true,
                showMenuIcon = false,
                onNavigationClick = { navController.popBackStack() }
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { paddingValues ->
        if (availableTrips.isEmpty()) {
            EmptyState(
                icon = Icons.Outlined.EventAvailable,
                title = "Nenhuma viagem disponível",
                description = "Não há viagens disponíveis para reserva no momento.",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "Próximas viagens disponíveis",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF374151),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(availableTrips) { trip ->
                    BookingTripCard(
                        trip = trip,
                        isSelected = selectedTripId == trip.id,
                        onSelect = { selectedTripId = trip.id }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            // Botão de confirmar reserva
            if (selectedTripId != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(
                        onClick = { showConfirmDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6366F1)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Confirmar Reserva",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }

    // Dialog de confirmação
    if (showConfirmDialog) {
        val selectedTrip = availableTrips.find { it.id == selectedTripId }
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = {
                Text(
                    text = "Confirmar Reserva",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text("Deseja confirmar a reserva para:")
                    Spacer(modifier = Modifier.height(8.dp))
                    selectedTrip?.let {
                        Text(
                            text = "${it.date} às ${it.time}",
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF6366F1)
                        )
                        Text(
                            text = "${it.origin} → ${it.destination}",
                            fontSize = 14.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmDialog = false
                        // TODO: Implementar lógica de reserva
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6366F1)
                    )
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Cancelar", color = Color(0xFF6B7280))
                }
            }
        )
    }
}

@Composable
private fun BookingTripCard(
    trip: AvailableTrip,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        onClick = onSelect,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFEEF2FF) else Color.White
        ),
        border = if (isSelected) {
            CardDefaults.outlinedCardBorder().copy(
                brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF6366F1))
            )
        } else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone e data
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(70.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = null,
                    tint = if (isSelected) Color(0xFF6366F1) else Color(0xFF9CA3AF),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = trip.date,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color(0xFF374151)
                )
                Text(
                    text = trip.time,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF6366F1)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Detalhes da viagem
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.DirectionsBus,
                        contentDescription = null,
                        tint = Color(0xFF9CA3AF),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = trip.origin,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color(0xFF374151)
                    )
                }
                Text(
                    text = "→ ${trip.destination}",
                    fontSize = 13.sp,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(start = 20.dp)
                )
            }

            // Vagas
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${trip.availableSeats}/${trip.totalSeats}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = if (trip.availableSeats > 10) Color(0xFF10B981) else Color(0xFFEF4444)
                )
                Text(
                    text = "vagas",
                    fontSize = 11.sp,
                    color = Color(0xFF9CA3AF)
                )
            }

            // Indicador de seleção
            if (isSelected) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Selecionado",
                    tint = Color(0xFF6366F1),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
