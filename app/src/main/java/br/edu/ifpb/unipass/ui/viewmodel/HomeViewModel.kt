package br.edu.ifpb.unipass.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpb.unipass.data.local.AppDatabase
import br.edu.ifpb.unipass.data.local.mapper.toEntity
import br.edu.ifpb.unipass.data.local.mapper.toTrip
import br.edu.ifpb.unipass.data.local.mapper.toUser
import br.edu.ifpb.unipass.data.repository.TripRepository
import br.edu.ifpb.unipass.models.TripStatus
import br.edu.ifpb.unipass.ui.state.HomeUiState
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val database: AppDatabase,
    private val tripRepository: TripRepository,
    private val userId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var tripListener: ListenerRegistration? = null

    companion object {
        private const val TAG = "HomeViewModel"
    }

    init {
        loadUserData()
        observeNextTrip()
    }

    /**
     * Carrega dados do usuário localmente (Room)
     */
    private fun loadUserData() {
        viewModelScope.launch {
            try {
                database.userDao()
                    .getUserById(userId)
                    .collect { userEntity ->
                        _uiState.update { state ->
                            state.copy(
                                user = userEntity?.toUser(),
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao carregar usuário", e)
                setError("Erro ao carregar dados do usuário")
            }
        }
    }

    /**
     * Observa a próxima viagem (offline-first)
     */
    private fun observeNextTrip() {
        observeLocalNextTrip()
        syncNextTripWithFirebase()
    }

    /**
     * Observa próxima viagem salva no Room
     */
    private fun observeLocalNextTrip() {
        viewModelScope.launch {
            try {
                database.tripDao()
                    .getNextTrip(userId)
                    .collect { tripEntity ->
                        _uiState.update { state ->
                            state.copy(
                                nextTrip = tripEntity?.toTrip(),
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao observar viagem local", e)
                setError("Erro ao carregar próxima viagem")
            }
        }
    }

    /**
     * Sincroniza próxima viagem com Firebase
     */
    private fun syncNextTripWithFirebase() {
        tripListener?.remove()

        tripListener = tripRepository.observeNextTrip(userId) { trip ->
            viewModelScope.launch {
                try {
                    if (trip != null) {
                        val entity = trip.toEntity(userId)
                        database.tripDao().insertTrip(entity)

                        _uiState.update { state ->
                            state.copy(
                                nextTrip = trip,
                                isLoading = false,
                                error = null
                            )
                        }
                    } else {
                        _uiState.update { state ->
                            state.copy(
                                nextTrip = null,
                                isLoading = false
                            )
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Erro ao sincronizar Firebase", e)
                    setError("Erro ao sincronizar viagem")
                }
            }
        }
    }

    /**
     * Cancela reserva da próxima viagem
     */
    fun onCancelReservation() {
        viewModelScope.launch {
            try {
                val trip = _uiState.value.nextTrip ?: return@launch

                val updatedTrip = trip.copy(status = TripStatus.CANCELLED)
                database.tripDao().updateTrip(updatedTrip.toEntity(userId))

                Log.d(TAG, "Reserva cancelada: ${trip.id}")
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao cancelar reserva", e)
                setError("Erro ao cancelar reserva")
            }
        }
    }

    fun onNotificationClick() {
        Log.d(TAG, "Notificação clicada")
    }

    fun onViewTripDetails() {
        Log.d(TAG, "Ver detalhes da viagem")
    }

    fun onViewMap() {
        Log.d(TAG, "Ver mapa")
    }

    fun onViewFullMap() {
        Log.d(TAG, "Ver mapa completo")
    }

    private fun setError(message: String) {
        _uiState.update {
            it.copy(
                error = message,
                isLoading = false
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        tripListener?.remove()
        Log.d(TAG, "ViewModel finalizado - listener removido")
    }
}
