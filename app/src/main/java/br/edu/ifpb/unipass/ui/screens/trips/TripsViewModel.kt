package br.edu.ifpb.unipass.ui.screens.trips

import androidx.lifecycle.viewModelScope
import br.edu.ifpb.unipass.data.local.AppDatabase
import br.edu.ifpb.unipass.data.local.mapper.toEntity
import br.edu.ifpb.unipass.data.local.mapper.toTrip
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import br.edu.ifpb.unipass.data.repository.TripRepository
import br.edu.ifpb.unipass.models.Trip
import br.edu.ifpb.unipass.models.TripStatus
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class TripFilter {
    ALL, COMPLETED, CANCELLED
}

data class TripsUiState(
    val allTrips: List<Trip> = emptyList(),
    val filteredTrips: List<Trip> = emptyList(),
    val selectedFilter: TripFilter = TripFilter.ALL,
    val isLoading: Boolean = true
)

class TripsViewModel(
    private val tripRepository: TripRepository,
    private val database: AppDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TripsUiState())
    val uiState: StateFlow<TripsUiState> = _uiState.asStateFlow()

    private var tripsListener: ListenerRegistration? = null
    private val currentUserId = "user123"

    init {
        observeTrips()
    }

    private fun observeTrips() {
        // 1. Carrega do Room primeiro (offline)
        viewModelScope.launch {
            database.tripDao().getAllTrips(currentUserId).collect { tripEntities ->
                val trips = tripEntities.map { it.toTrip() }
                _uiState.update { state ->
                    state.copy(
                        allTrips = trips,
                        isLoading = false
                    )
                }
                applyFilter(_uiState.value.selectedFilter)
            }
        }

        // 2. Sincroniza com Firebase
        tripsListener = tripRepository.observeUserTripHistory(currentUserId) { trips ->
            viewModelScope.launch {
                val tripEntities = trips.map { it.toEntity(currentUserId) }
                database.tripDao().insertTrips(tripEntities)
            }
        }
    }

    fun onFilterSelected(filter: TripFilter) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
        applyFilter(filter)
    }

    private fun applyFilter(filter: TripFilter) {
        val allTrips = _uiState.value.allTrips
        val filtered = when (filter) {
            TripFilter.ALL -> allTrips
            TripFilter.COMPLETED -> allTrips.filter { it.status == TripStatus.COMPLETED }
            TripFilter.CANCELLED -> allTrips.filter {
                it.status == TripStatus.CANCELLED || it.status == TripStatus.NO_SHOW
            }
        }
        _uiState.update { it.copy(filteredTrips = filtered) }
    }

    override fun onCleared() {
        super.onCleared()
        tripsListener?.remove()
    }
}
