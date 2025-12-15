package br.edu.ifpb.unipass.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpb.unipass.models.StudentCard

@Composable
fun StudentCardView(
    studentCard: StudentCard,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF4A6FA5)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StudentPhoto()

            Spacer(modifier = Modifier.height(16.dp))

            StudentName(name = studentCard.studentName)

            Spacer(modifier = Modifier.height(12.dp))

            InstitutionInfo(institution = studentCard.institution)

            Spacer(modifier = Modifier.height(8.dp))

            CourseInfo(course = studentCard.course)

            Spacer(modifier = Modifier.height(4.dp))

            ShiftInfo(shift = studentCard.shift)

            Spacer(modifier = Modifier.height(20.dp))

            CardNumber(number = studentCard.cardNumber)

            Spacer(modifier = Modifier.height(16.dp))

            StatusBadge(isActive = studentCard.isActive)

            Spacer(modifier = Modifier.height(8.dp))

            ValidUntilText(validUntil = studentCard.validUntil)
        }
    }
}

@Composable
private fun StudentPhoto() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.School,
            contentDescription = "Foto do estudante",
            modifier = Modifier.size(50.dp),
            tint = Color(0xFF4A6FA5)
        )
    }
}

@Composable
private fun StudentName(name: String) {
    Text(
        text = name,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        letterSpacing = 1.sp
    )
}

@Composable
private fun InstitutionInfo(institution: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.School,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = institution,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun CourseInfo(course: String) {
    Text(
        text = course,
        color = Color.White,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun ShiftInfo(shift: String) {
    Text(
        text = "Turno: $shift",
        color = Color.White,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal
    )
}

@Composable
private fun CardNumber(number: String) {
    Text(
        text = "Nº $number",
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp
    )
}

@Composable
private fun StatusBadge(isActive: Boolean) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (isActive) Color(0xFF8BC34A) else Color(0xFFE57373)
    ) {
        Text(
            text = if (isActive) "ATIVA" else "INATIVA",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun ValidUntilText(validUntil: String) {
    Text(
        text = "Válida até $validUntil",
        color = Color.White.copy(alpha = 0.9f),
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    )
}
