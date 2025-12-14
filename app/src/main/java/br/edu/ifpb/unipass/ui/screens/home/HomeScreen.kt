package br.edu.ifpb.unipass.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.edu.ifpb.unipass.navigation.Routes
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.HeadsetMic


@Composable
fun HomeScreen(navController: NavController) { // desenha a interface da Home; recebe um navController para navegação

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // TOPO
        Text(
            text = "Olá, estudante 👋",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ACESSO RÁPIDO
        Text(
            text = "Acesso rápido",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

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
                onClick = { }
            )
            QuickAccessItem(
                icon = Icons.AutoMirrored.Filled.List,
                label = "Histórico",
                onClick = { }
            )
            QuickAccessItem(
                icon = Icons.Filled.HeadsetMic,
                label = "Suporte",
                onClick = { }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PRÓXIMA VIAGEM
        Text(
            text = "Próxima viagem",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(text = "Hoje, 07:30", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Centro → UFPB (Campus I)")
                Text(text = "Assento 12A")
                Text(text = "38/40 reservas")

                Spacer(modifier = Modifier.height(12.dp))

                //LinearProgressIndicator(progress = 0.95f)

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { }) {
                        Text("Ver no mapa")
                    }
                    TextButton(onClick = { }) {
                        Text(
                            text = "Cancelar reserva",
                            color = Color.Red
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ÔNIBUS EM TEMPO REAL (placeholder)
        Text(
            text = "Ônibus em tempo real",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(
                    color = Color(0xFFE3F2FD),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("Mapa em tempo real (em breve)")
        }
    }
}

@Composable
fun QuickAccessItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(80.dp)
            .clickable { onClick() }
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

