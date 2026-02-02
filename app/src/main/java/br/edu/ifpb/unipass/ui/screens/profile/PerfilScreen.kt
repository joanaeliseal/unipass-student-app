package br.edu.ifpb.unipass.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.ifpb.unipass.navigation.Routes

@Composable
fun PerfilScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Dados mockados - TODO: Integrar com banco de dados
    val userName = "Maria Silva Santos"
    val userEmail = "maria.santos@estudante.edu.br"
    val userCpf = "123.456.789-00"
    val userCourse = "Engenharia de Software"
    val userInstitution = "IFPB - Campus João Pessoa"
    val completedTrips = 47
    val scheduledTrips = 3

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .verticalScroll(rememberScrollState())
    ) {
        // Header com foto e nome
        ProfileHeader(
            userName = userName,
            userEmail = userEmail,
            onEditClick = { /* TODO: Implementar edição */ }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Estatísticas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                value = completedTrips.toString(),
                label = "Viagens\nRealizadas",
                color = Color(0xFF10B981)
            )
            StatCard(
                modifier = Modifier.weight(1f),
                value = scheduledTrips.toString(),
                label = "Viagens\nAgendadas",
                color = Color(0xFF6366F1)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Informações pessoais
        SectionTitle(title = "Informações Pessoais")

        InfoCard(
            items = listOf(
                InfoItem(Icons.Filled.Person, "Nome completo", userName),
                InfoItem(Icons.Filled.Email, "E-mail", userEmail),
                InfoItem(Icons.Filled.Badge, "CPF", userCpf),
                InfoItem(Icons.Filled.School, "Curso", userCourse),
                InfoItem(Icons.Filled.Business, "Instituição", userInstitution)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Configurações
        SectionTitle(title = "Configurações")

        SettingsCard(
            items = listOf(
                SettingsItem(Icons.Filled.Notifications, "Notificações", "Gerenciar alertas"),
                SettingsItem(Icons.Filled.Lock, "Privacidade", "Configurações de privacidade"),
                SettingsItem(Icons.AutoMirrored.Filled.Help, "Ajuda", "Central de ajuda")
            ),
            onItemClick = { /* TODO: Implementar navegação */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de logout
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.LOGIN) {
                    popUpTo(Routes.HOME) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFEF4444)
            ),
            border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFEF4444))
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sair da conta",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Versão do app
        Text(
            text = "Versão 3.0.0",
            fontSize = 12.sp,
            color = Color(0xFF9CA3AF),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun ProfileHeader(
    userName: String,
    userEmail: String,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF6366F1))
            .padding(top = 32.dp, bottom = 40.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    color = Color.White
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = userName.split(" ")
                                .take(2)
                                .mapNotNull { it.firstOrNull()?.uppercase() }
                                .joinToString(""),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6366F1)
                        )
                    }
                }

                // Botão de editar
                Surface(
                    onClick = onEditClick,
                    modifier = Modifier
                        .size(32.dp)
                        .offset(x = 4.dp, y = 4.dp),
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Editar perfil",
                            tint = Color(0xFF6366F1),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = userName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = userEmail,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF374151),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

data class InfoItem(
    val icon: ImageVector,
    val label: String,
    val value: String
)

@Composable
private fun InfoCard(items: List<InfoItem>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFEEF2FF),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                tint = Color(0xFF6366F1),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.label,
                            fontSize = 12.sp,
                            color = Color(0xFF9CA3AF)
                        )
                        Text(
                            text = item.value,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF374151)
                        )
                    }
                }

                if (index < items.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = Color(0xFFF3F4F6)
                    )
                }
            }
        }
    }
}

data class SettingsItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String
)

@Composable
private fun SettingsCard(
    items: List<SettingsItem>,
    onItemClick: (SettingsItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            items.forEachIndexed { index, item ->
                Surface(
                    onClick = { onItemClick(item) },
                    color = Color.Transparent
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF3F4F6),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null,
                                    tint = Color(0xFF6B7280),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF374151)
                            )
                            Text(
                                text = item.subtitle,
                                fontSize = 13.sp,
                                color = Color(0xFF9CA3AF)
                            )
                        }

                        Icon(
                            imageVector = Icons.Filled.ChevronRight,
                            contentDescription = null,
                            tint = Color(0xFFD1D5DB),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                if (index < items.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = Color(0xFFF3F4F6)
                    )
                }
            }
        }
    }
}
