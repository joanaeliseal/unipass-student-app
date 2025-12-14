package br.edu.ifpb.unipass.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.edu.ifpb.unipass.R
import br.edu.ifpb.unipass.navigation.Routes

@Composable
fun LoginScreen(navController: NavController) {

    var cpf by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        // Ícone / Logo
        Image(
            painter = painterResource(id = R.drawable.logo_sape),
            contentDescription = "Logo Prefeitura de Sapé",
            modifier = Modifier.size(96.dp).padding(bottom = 16.dp)
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
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // CPF
        OutlinedTextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Senha
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Esqueceu a senha
        TextButton(
            onClick = { /* futuro */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Esqueceu a senha?")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botão Entrar
        Button(
            onClick = {
                // validação simples para Etapa 1
                if (cpf.isNotBlank() && senha.isNotBlank()) {
                    navController.navigate(Routes.HOME)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Criar conta
        TextButton(onClick = { /* futuro */ }) {
            Text("Criar nova conta")
        }
    }
}

