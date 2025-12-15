package br.edu.ifpb.unipass.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardIndicator(
    totalCards: Int = 1,
    currentCard: Int = 0,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalCards) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == currentCard) 8.dp else 6.dp)
                    .background(
                        color = if (index == currentCard) Color(0xFF6366F1) else Color(0xFFD1D5DB),
                        shape = CircleShape
                    )
            )
            if (index < totalCards - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}
