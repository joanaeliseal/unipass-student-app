package br.edu.ifpb.unipass.ui.screens.trips

import androidx.lifecycle.ViewModel
import br.edu.ifpb.unipass.data.repository.TripRepository
import br.edu.ifpb.unipass.models.Trip
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
    private val tripRepository: TripRepository = TripRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(TripsUiState())
    val uiState: StateFlow<TripsUiState> = _uiState.asStateFlow()

    private var tripsListener: ListenerRegistration? = null

    init {
        observeTrips()
    }

    private fun observeTrips() {
        // TODO: Substituir "user123" pelo ID do usuário logado
        tripsListener = tripRepository.observeUserTripHistory("user123") { trips ->
            _uiState.value = _uiState.value.copy(
                allTrips = trips,
                isLoading = false
            )
            applyFilter(_uiState.value.selectedFilter)
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
            TripFilter.COMPLETED -> allTrips.filter { it.status == "COMPLETED" }
            TripFilter.CANCELLED -> allTrips.filter {
                it.status == "CANCELLED" || it.status == "NO_SHOW"
            }
        }
        _uiState.value = _uiState.value.copy(filteredTrips = filtered)
    }

    override fun onCleared() {
        super.onCleared()
        tripsListener?.remove()
    }
}
