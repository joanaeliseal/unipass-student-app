package br.edu.ifpb.unipass.ui.state.trips

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpb.unipass.data.local.AppDatabase
import br.edu.ifpb.unipass.data.local.mapper.toEntity
import br.edu.ifpb.unipass.data.local.mapper.toTrip
import br.edu.ifpb.unipass.data.repository.TripRepository
import br.edu.ifpb.unipass.models.Trip
import br.edu.ifpb.unipass.models.TripStatus
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class TripFilter {
    ALL, COMPLETED, CANCELLED
}

data class TripsUiState(
    val allTrips: List<Trip> = emptyList(),
    val filteredTrips: List<Trip> = emptyList(),
    val selectedFilter: TripFilter = TripFilter.ALL,
    val isLoading: Boolean = true,
    val error: String? = null
)

class TripsViewModel(
    private val tripRepository: TripRepository,
    private val database: AppDatabase,
    private val userId: String // usuário logado
) : ViewModel() {

    private val _uiState = MutableStateFlow(TripsUiState())
    val uiState: StateFlow<TripsUiState> = _uiState.asStateFlow()

    private var tripListener: ListenerRegistration? = null

    companion object {
        private const val TAG = "TripsViewModel"
    }

    init {
        observeTrips()
    }

    /**
     * Estratégia offline-first:
     * 1. Observa dados locais (Room)
     * 2. Sincroniza com Firebase em tempo real
     */
    private fun observeTrips() {
        observeLocalTrips()
        syncWithFirebase()
    }

    /**
     * Observa viagens salvas localmente (Room)
     */
    private fun observeLocalTrips() {
        viewModelScope.launch {
            try {
                database.tripDao().getAllTrips(userId).collect { entities ->
                    val trips = entities.map { it.toTrip() }
                    updateTrips(trips)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao observar viagens locais", e)
                setError(e.message)
            }
        }
    }

    /**
     * Sincroniza viagens do Firebase e persiste no Room
     */
    private fun syncWithFirebase() {
        tripListener?.remove()

        tripListener = tripRepository.observeUserTripHistory(userId) { trips ->
            viewModelScope.launch {
                try {
                    val entities = trips.map { it.toEntity(userId) }
                    database.tripDao().insertTrips(entities)

                    Log.d(TAG, "Firebase sincronizado: ${trips.size} viagens")
                } catch (e: Exception) {
                    Log.e(TAG, "Erro ao sincronizar Firebase", e)
                    setError(e.message)
                }
            }
        }
    }

    /**
     * Atualiza lista de viagens e reaplica filtro
     */
    private fun updateTrips(trips: List<Trip>) {
        _uiState.update { state ->
            val filtered = filterTrips(trips, state.selectedFilter)
            state.copy(
                allTrips = trips,
                filteredTrips = filtered,
                isLoading = false,
                error = null
            )
        }
    }

    /**
     * Aplica filtro às viagens
     */
    private fun filterTrips(
        trips: List<Trip>,
        filter: TripFilter
    ): List<Trip> {
        return when (filter) {
            TripFilter.ALL -> trips
            TripFilter.COMPLETED ->
                trips.filter { it.status == TripStatus.COMPLETED }

            TripFilter.CANCELLED ->
                trips.filter {
                    it.status == TripStatus.CANCELLED ||
                            it.status == TripStatus.NO_SHOW
                }
        }
    }

    /**
     * Evento de mudança de filtro
     */
    fun onFilterSelected(filter: TripFilter) {
        _uiState.update { state ->
            val filtered = filterTrips(state.allTrips, filter)
            state.copy(
                selectedFilter = filter,
                filteredTrips = filtered
            )
        }
        Log.d(TAG, "Filtro selecionado: $filter")
    }

    /**
     * Atualiza estado de erro
     */
    private fun setError(message: String?) {
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
