package br.edu.ifpb.unipass.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpb.unipass.models.Trip
import br.edu.ifpb.unipass.models.TripStatus

@Composable
fun TripCard(
    trip: Trip,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TripDateTime(dateTime = trip.dateTime)
                TripStatusBadge(status = trip.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TripInfoRow(
                icon = Icons.Default.LocationOn,
                text = trip.route
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TripInfoRow(
                    icon = Icons.Default.EventSeat,
                    text = "Assento ${trip.seatNumber}",
                    modifier = Modifier.weight(1f)
                )

                TripInfoRow(
                    icon = Icons.Default.DirectionsBus,
                    text = trip.time,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TripDateTime(dateTime: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Schedule,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Color(0xFF6B7280)
        )
        Text(
            text = dateTime,
            fontSize = 13.sp,
            color = Color(0xFF6B7280),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun TripStatusBadge(status: TripStatus) {
    val (statusText, statusColor) = when (status) {
        TripStatus.SCHEDULED -> "Agendada" to Color(0xFF6366F1)
        TripStatus.COMPLETED -> "Concluída" to Color(0xFF10B981)
        TripStatus.CANCELLED -> "Cancelada" to Color(0xFFEF4444)
        TripStatus.NO_SHOW -> "Não compareceu" to Color(0xFFF59E0B)
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = statusColor.copy(alpha = 0.1f)
    ) {
        Text(
            text = statusText,
            color = statusColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun TripInfoRow(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF6B7280),
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color(0xFF374151),
            fontWeight = FontWeight.Normal
        )
    }
}
