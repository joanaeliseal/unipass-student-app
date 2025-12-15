package br.edu.ifpb.unipass.ui.screens.studentCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.edu.ifpb.unipass.models.StudentCard
import br.edu.ifpb.unipass.ui.components.BrightnessButton
import br.edu.ifpb.unipass.ui.components.CardIndicator
import br.edu.ifpb.unipass.ui.components.StudentCardView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert


@Composable
fun StudentCardScreen(navController: NavController) {
    val studentCard = StudentCard(
        studentName = "JOÃO DA SILVA SANTOS",
        institution = "UFPB - Campus I",
        course = "Engenharia de Software",
        shift = "Noturno",
        cardNumber = "2025001234",
        isActive = true,
        validUntil = "31/12/2025"
    )

    StudentCardContent(
        studentCard = studentCard,
        onMenuClick = { },
        onBrightnessClick = { },
        onHowToUseClick = { }
    )
}

@Composable
private fun StudentCardContent(
    studentCard: StudentCard,
    onMenuClick: () -> Unit,
    onBrightnessClick: () -> Unit,
    onHowToUseClick: () -> Unit
) {
    Scaffold(
        topBar = {
            StudentCardTopBar(
                title = "Carteirinha Digital",
                onMenuClick = onMenuClick
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                StudentCardView(studentCard = studentCard)

                Spacer(modifier = Modifier.height(24.dp))

                CardIndicator(
                    totalCards = 1,
                    currentCard = 0
                )

                Spacer(modifier = Modifier.height(24.dp))

                HowToUseLink(onClick = onHowToUseClick)

                Spacer(modifier = Modifier.height(120.dp))
            }

            BrightnessButton(
                onClick = onBrightnessClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 80.dp, end = 16.dp)
            )
        }
    }
}

@Composable
private fun HowToUseLink(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = "Como usar?",
            color = Color(0xFF6366F1),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudentCardTopBar(
    title: String,
    onMenuClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        actions = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Filled.MoreVert,
                    contentDescription = "Menu"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black
        )
    )
}

@Composable
fun CarteirinhaScreen(navController: NavController) {
    StudentCardScreen(navController)
}
