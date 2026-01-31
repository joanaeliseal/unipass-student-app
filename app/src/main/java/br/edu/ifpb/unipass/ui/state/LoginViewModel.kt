package br.edu.ifpb.unipass.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpb.unipass.data.local.AppDatabase
import br.edu.ifpb.unipass.ui.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val database: AppDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    companion object {
        private const val TAG = "LoginViewModel"
    }

    fun onCpfChange(cpf: String) {
        // Remove caracteres não numéricos e limita a 11 dígitos
        val filteredCpf = cpf.filter { it.isDigit() }.take(11)
        _uiState.update { it.copy(cpf = filteredCpf) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }

                val cpf = _uiState.value.cpf
                val password = _uiState.value.password

                // Validações
                if (cpf.length != 11) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "CPF deve conter 11 dígitos"
                        )
                    }
                    return@launch
                }

                if (password.isBlank()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Senha é obrigatória"
                        )
                    }
                    return@launch
                }

                // TODO: Implementar autenticação real com Firebase
                // Por enquanto, apenas verifica se o usuário existe no banco local
                val user = database.userDao().getUserByCpf(cpf)

                if (user != null) {
                    Log.d(TAG, "Login bem-sucedido para: ${user.name}")
                    _uiState.update { it.copy(isLoading = false) }
                    onSuccess()
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "CPF ou senha inválidos"
                        )
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "Erro ao fazer login: ${e.message}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Erro ao fazer login. Tente novamente."
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}


