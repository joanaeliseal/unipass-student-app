package br.edu.ifpb.unipass.ui.screens.home

import androidx.lifecycle.ViewModel
import br.edu.ifpb.unipass.data.repository.TripRepository
import br.edu.ifpb.unipass.models.Trip
import br.edu.ifpb.unipass.models.User
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeUiState(
    val currentUser: User = User(
        name = "Maria",
        initials = "MS",
        notificationCount = 3
    ),
    val nextTrip: Trip? = null,
    val isLoading: Boolean = true
)

class HomeViewModel(
    private val tripRepository: TripRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var tripListener: ListenerRegistration? = null

    init {
        observeNextTrip()
    }

    private fun observeNextTrip() {
        tripListener = tripRepository.observeNextTrip { trip ->
            _uiState.value = _uiState.value.copy(
                nextTrip = trip,
                isLoading = false
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        tripListener?.remove()
    }
}
