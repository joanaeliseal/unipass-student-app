package br.edu.ifpb.unipass.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import br.edu.ifpb.unipass.R
import br.edu.ifpb.unipass.navigation.Routes
import br.edu.ifpb.unipass.ui.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_sape),
            contentDescription = "Logo Prefeitura de Sapé",
            modifier = Modifier.size(56.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Bem vindo de volta",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Faça login para continuar",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "CPF",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = uiState.cpf,
            onValueChange = { viewModel.onCpfChange(it) },
            placeholder = { Text("000.000.000-00", color = Color.LightGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "CPF",
                    tint = Color.Gray
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.error != null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Senha",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            placeholder = { Text("Digite sua senha", color = Color.LightGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Senha",
                    tint = Color.Gray
                )
            },
            trailingIcon = {
                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                    Icon(
                        imageVector = if (uiState.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (uiState.isPasswordVisible) "Ocultar senha" else "Mostrar senha",
                        tint = Color.Gray
                    )
                }
            },
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.error != null
        )

        // Mensagem de erro
        if (uiState.error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = uiState.error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { /* futuro */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                "Esqueceu a senha?",
                color = Color(0xFF6366F1)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.onLoginClick {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = uiState.cpf.isNotBlank() && uiState.password.isNotBlank() && !uiState.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6366F1),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFE5E7EB),
                disabledContentColor = Color(0xFF9CA3AF)
            )
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Entrar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ou",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { /* futuro */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Criar nova conta",
                color = Color(0xFF6366F1)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Dica para teste
        Text(
            text = "CPF de teste: 12345678900",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "v3.0.0",
            style = MaterialTheme.typography.bodySmall,
            color = Color.LightGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}
