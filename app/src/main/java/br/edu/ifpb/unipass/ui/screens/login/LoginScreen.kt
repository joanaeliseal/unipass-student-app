package br.edu.ifpb.unipass.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.navigation.NavController
import br.edu.ifpb.unipass.R
import br.edu.ifpb.unipass.navigation.Routes

@Composable
fun LoginScreen(navController: NavController) {

    var cpf by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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

        // Título
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

        // Label CPF
        Text(
            text = "CPF",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        // CPF Field com ícone
        OutlinedTextField(
            value = cpf,
            onValueChange = { newValue ->
                // Aceita apenas números e limita a 11 dígitos
                if (newValue.filter { it.isDigit() }.length <= 11) {
                    cpf = newValue.filter { it.isDigit() }
                }
            },
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
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Label Senha
        Text(
            text = "Senha",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        // Senha Field com ícones
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            placeholder = { Text("Digite sua senha", color = Color.LightGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Senha",
                    tint = Color.Gray
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha",
                        tint = Color.Gray
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Esqueceu a senha
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

        // Botão Entrar
        Button(
            onClick = {
                navController.navigate(Routes.HOME)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = cpf.isNotBlank() && senha.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE5E7EB),
                contentColor = Color(0xFF374151),
                disabledContainerColor = Color(0xFFF5F5F5),
                disabledContentColor = Color(0xFFD1D5DB)
            )
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Divider "ou"
        Text(
            text = "ou",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Criar conta
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

        // Version
        Text(
            text = "v3.0.0",
            style = MaterialTheme.typography.bodySmall,
            color = Color.LightGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

