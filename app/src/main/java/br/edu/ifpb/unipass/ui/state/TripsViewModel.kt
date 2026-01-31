package br.edu.ifpb.unipass.ui.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpb.unipass.data.local.AppDatabase
import br.edu.ifpb.unipass.data.local.mapper.toEntity
import br.edu.ifpb.unipass.data.local.mapper.toTrip
import br.edu.ifpb.unipass.repository.TripRepository
import br.edu.ifpb.unipass.ui.state.TripFilter
import br.edu.ifpb.unipass.ui.state.TripsUiState
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TripsViewModel(
    private val database: AppDatabase,
    private val tripRepository: TripRepository = TripRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(TripsUiState())
    val uiState: StateFlow<TripsUiState> = _uiState.asStateFlow()

    private var tripListener: ListenerRegistration? = null
    private val currentUserId = "user123" // TODO: Substituir pelo ID do usuário logado

    companion object {
        private const val TAG = "TripsViewModel"
    }

    init {
        observeTrips()
    }

    private fun observeTrips() {
        viewModelScope.launch {
            try {
                // Carrega do banco local primeiro
                database.tripDao().getAllTrips(currentUserId).collect { tripEntities ->
                    val trips = tripEntities.map { it.toTrip() }
                    _uiState.update {
                        it.copy(
                            trips = filterTrips(trips, it.selectedFilter),
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao observar viagens: ${e.message}")
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }

        // Sincroniza com Firebase
        syncWithFirebase()
    }

    private fun syncWithFirebase() {
        tripListener = tripRepository.observeUserTripHistory(currentUserId) { trips ->
            viewModelScope.launch {
                try {
                    // Salva no banco local
                    val tripEntities = trips.map { it.toEntity(currentUserId) }
                    database.tripDao().insertTrips(tripEntities)

                    _uiState.update {
                        it.copy(
                            trips = filterTrips(trips, it.selectedFilter),
                            isLoading = false
                        )
                    }

                    Log.d(TAG, "Sincronizado ${trips.size} viagens com Firebase")
                } catch (e: Exception) {
                    Log.e(TAG, "Erro ao sincronizar com Firebase: ${e.message}")
                }
            }
        }
    }

    fun onFilterChange(filter: TripFilter) {
        viewModelScope.launch {
            val currentTrips = _uiState.value.trips
            _uiState.update {
                it.copy(
                    selectedFilter = filter,
                    trips = filterTrips(currentTrips, filter)
                )
            }
            Log.d(TAG, "Filtro alterado para: $filter")
        }
    }

    private fun filterTrips(trips: List<br.edu.ifpb.unipass.models.Trip>, filter: TripFilter): List<br.edu.ifpb.unipass.models.Trip> {
        return when (filter) {
            TripFilter.ALL -> trips
            TripFilter.COMPLETED -> trips.filter { it.status == "COMPLETED" }
            TripFilter.CANCELLED -> trips.filter {
                it.status == "CANCELLED" || it.status == "NO_SHOW"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        tripListener?.remove()
        Log.d(TAG, "ViewModel cleared - listener removido")
    }
}