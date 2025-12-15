package br.edu.ifpb.unipass.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
@Composable
fun RealTimeMapSection(
    onViewFullMap: () -> Unit,
    modifier: Modifier = Modifier
) {
    MapPreviewCard(onViewFullMap = onViewFullMap, modifier = modifier)
}

@Composable
private fun MapPreviewCard(
    onViewFullMap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE0E7FF),
                            Color(0xFFF3F4F6)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ônibus em tempo real",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1F2937)
                )

                TextButton(onClick = onViewFullMap) {
                    Text(
                        text = "Ver mapa completo",
                        color = Color(0xFF6B7280)
                    )
                }



            }

            // Marcadores de ônibus
            Box(modifier = Modifier.fillMaxSize()) {
                BusLocationMarker(
                    color = Color(0xFF8B5CF6),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 40.dp, y = 20.dp)
                )

                BusLocationMarker(
                    color = Color(0xFF3B82F6),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = (-20).dp, y = 10.dp)
                )

                BusLocationMarker(
                    color = Color(0xFF8B5CF6),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-40).dp, y = 8.dp)
                )
            }


        }
    }
}

@Composable
private fun BusLocationMarker(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .background(
                color = color,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Bus location",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
    }
}