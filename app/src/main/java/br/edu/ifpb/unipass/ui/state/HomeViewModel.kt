package br.edu.ifpb.unipass.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpb.unipass.data.local.AppDatabase
import br.edu.ifpb.unipass.data.local.mapper.toTrip
import br.edu.ifpb.unipass.data.local.mapper.toUser
import br.edu.ifpb.unipass.repository.TripRepository
import br.edu.ifpb.unipass.ui.state.HomeUiState
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val database: AppDatabase,
    private val tripRepository: TripRepository = TripRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var tripListener: ListenerRegistration? = null
    private val currentUserId = "user123" // TODO: Substituir pelo ID do usuário logado

    companion object {
        private const val TAG = "HomeViewModel"
    }

    init {
        loadUserData()
        observeNextTrip()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                database.userDao().getUserById(currentUserId).collect { userEntity ->
                    if (userEntity != null) {
                        _uiState.update { it.copy(user = userEntity.toUser()) }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao carregar dados do usuário: ${e.message}")
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    private fun observeNextTrip() {
        viewModelScope.launch {
            try {
                // Primeiro tenta carregar do banco local
                database.tripDao().getNextTrip(currentUserId).collect { tripEntity ->
                    _uiState.update {
                        it.copy(
                            nextTrip = tripEntity?.toTrip(),
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao observar próxima viagem: ${e.message}")
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }

        // Sincroniza com Firebase
        syncWithFirebase()
    }

    private fun syncWithFirebase() {
        tripListener = tripRepository.observeNextTrip { trip ->
            viewModelScope.launch {
                try {
                    if (trip != null) {
                        // Salva no banco local
                        val tripEntity = trip.toEntity(currentUserId)
                        database.tripDao().insertTrip(tripEntity)

                        _uiState.update {
                            it.copy(
                                nextTrip = trip,
                                isLoading = false
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                nextTrip = null,
                                isLoading = false
                            )
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Erro ao sincronizar com Firebase: ${e.message}")
                }
            }
        }
    }

    fun onNotificationClick() {
        Log.d(TAG, "Notificação clicada")
        // TODO: Implementar navegação para notificações
    }

    fun onViewTripDetails() {
        Log.d(TAG, "Ver detalhes da viagem")
        // TODO: Implementar navegação para detalhes da viagem
    }

    fun onViewMap() {
        Log.d(TAG, "Ver mapa")
        // TODO: Implementar navegação para mapa
    }

    fun onCancelReservation() {
        viewModelScope.launch {
            try {
                val trip = _uiState.value.nextTrip
                if (trip != null) {
                    // Atualiza status para cancelado
                    val updatedTrip = trip.copy(status = "CANCELLED")
                    val tripEntity = updatedTrip.toEntity(currentUserId)
                    database.tripDao().updateTrip(tripEntity)

                    Log.d(TAG, "Reserva cancelada: ${trip.id}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao cancelar reserva: ${e.message}")
                _uiState.update { it.copy(error = "Erro ao cancelar reserva") }
            }
        }
    }

    fun onViewFullMap() {
        Log.d(TAG, "Ver mapa completo")
        // TODO: Implementar navegação para mapa completo
    }

    override fun onCleared() {
        super.onCleared()
        tripListener?.remove()
        Log.d(TAG, "ViewModel cleared - listener removido")
    }
}