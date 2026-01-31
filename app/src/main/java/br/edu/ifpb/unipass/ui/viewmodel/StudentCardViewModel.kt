package br.edu.ifpb.unipass.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpb.unipass.data.local.AppDatabase
import br.edu.ifpb.unipass.data.local.mapper.toStudentCard
import br.edu.ifpb.unipass.ui.state.StudentCardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudentCardViewModel(
    private val database: AppDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentCardUiState())
    val uiState: StateFlow<StudentCardUiState> = _uiState.asStateFlow()

    private val currentUserId = "user123" // TODO: Substituir pelo ID do usuário logado

    companion object {
        private const val TAG = "StudentCardViewModel"
    }

    init {
        loadStudentCard()
    }

    private fun loadStudentCard() {
        viewModelScope.launch {
            try {
                database.studentCardDao().getStudentCardByUserId(currentUserId).collect { cardEntity ->
                    _uiState.update {
                        it.copy(
                            studentCard = cardEntity?.toStudentCard(),
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao carregar carteirinha: ${e.message}")
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun onBrightnessClick() {
        Log.d(TAG, "Ajustar brilho")
        // TODO: Implementar ajuste de brilho
    }

    fun onHowToUseClick() {
        Log.d(TAG, "Como usar")
        // TODO: Implementar navegação para tutorial
    }

    fun onMenuClick() {
        Log.d(TAG, "Menu clicado")
        // TODO: Implementar menu
    }
}