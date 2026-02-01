package br.edu.ifpb.unipass.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.edu.ifpb.unipass.navigation.Routes

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Header
        Text(
            text = "Olá, Estudante!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF374151)
        )

        Text(
            text = "Bem-vindo ao UniPass",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF6B7280)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Acesso Rápido
        Text(
            text = "Acesso Rápido",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF374151)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickAccessItem(
                icon = Icons.Filled.DateRange,
                label = "Reservar",
                onClick = { navController.navigate(Routes.RESERVA) }
            )
            QuickAccessItem(
                icon = Icons.Filled.AccessTime,
                label = "Horários",
                onClick = { navController.navigate(Routes.HORARIOS) }
            )
            QuickAccessItem(
                icon = Icons.AutoMirrored.Filled.List,
                label = "Histórico",
                onClick = { navController.navigate(Routes.CARTEIRINHA) }
            )
            QuickAccessItem(
                icon = Icons.Filled.HeadsetMic,
                label = "Suporte",
                onClick = { navController.navigate(Routes.SUPORTE) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Próxima Viagem
        Text(
            text = "Próxima Viagem",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF374151)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Hoje, 07:30",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6366F1)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sapé → IFPB Campus João Pessoa",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF374151)
                )

                Text(
                    text = "Assento 12A • 38/40 reservas",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = { 0.95f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = Color(0xFF6366F1),
                    trackColor = Color(0xFFE5E7EB)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { }) {
                        Text("Ver no mapa", color = Color(0xFF6366F1))
                    }
                    TextButton(onClick = { }) {
                        Text("Cancelar reserva", color = Color(0xFFEF4444))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Mapa em tempo real
        Text(
            text = "Ônibus em Tempo Real",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF374151)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    color = Color(0xFFEEF2FF),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Mapa em tempo real (em breve)",
                color = Color(0xFF6366F1)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun QuickAccessItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFEEF2FF),
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color(0xFF6366F1),
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF374151)
        )
    }
}
