package br.edu.ifpb.unipass.ui.screens.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.ifpb.unipass.ui.components.AppTopBar
import br.edu.ifpb.unipass.ui.components.EmptyState

data class ScheduleItem(
    val time: String,
    val route: String,
    val origin: String,
    val destination: String,
    val availableSeats: Int
)

@Composable
fun ScheduleScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Dados de exemplo - TODO: Integrar com banco de dados
    val schedules = listOf(
        ScheduleItem("06:30", "Linha 01", "Sapé", "IFPB - Campus João Pessoa", 15),
        ScheduleItem("07:00", "Linha 01", "Sapé", "UFPB - Campus I", 8),
        ScheduleItem("12:00", "Linha 02", "IFPB - Campus João Pessoa", "Sapé", 20),
        ScheduleItem("12:30", "Linha 02", "UFPB - Campus I", "Sapé", 12),
        ScheduleItem("17:30", "Linha 01", "Sapé", "IFPB - Campus João Pessoa", 10),
        ScheduleItem("18:00", "Linha 01", "Sapé", "UFPB - Campus I", 5),
        ScheduleItem("22:00", "Linha 02", "IFPB - Campus João Pessoa", "Sapé", 18),
        ScheduleItem("22:30", "Linha 02", "UFPB - Campus I", "Sapé", 14)
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = "Horários",
                showNavigationIcon = true,
                showMenuIcon = false,
                onNavigationClick = { navController.popBackStack() }
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { paddingValues ->
        if (schedules.isEmpty()) {
            EmptyState(
                icon = Icons.Outlined.Schedule,
                title = "Nenhum horário disponível",
                description = "Não há horários cadastrados no momento.",
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
                        text = "Horários disponíveis",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF374151),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(schedules) { schedule ->
                    ScheduleCard(schedule = schedule)
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun ScheduleCard(schedule: ScheduleItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Horário
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = null,
                    tint = Color(0xFF6366F1),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = schedule.time,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF374151)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Detalhes da rota
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = schedule.route,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF6366F1)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${schedule.origin} → ${schedule.destination}",
                    fontSize = 13.sp,
                    color = Color(0xFF6B7280)
                )
            }

            // Vagas disponíveis
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${schedule.availableSeats}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = if (schedule.availableSeats > 5) Color(0xFF10B981) else Color(0xFFEF4444)
                )
                Text(
                    text = "vagas",
                    fontSize = 12.sp,
                    color = Color(0xFF9CA3AF)
                )
            }
        }
    }
}
