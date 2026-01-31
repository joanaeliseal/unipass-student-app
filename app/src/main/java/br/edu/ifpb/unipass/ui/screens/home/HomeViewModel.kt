package br.edu.ifpb.unipass.ui.screens.home

import androidx.lifecycle.viewModelScope
import br.edu.ifpb.unipass.data.local.AppDatabase
import br.edu.ifpb.unipass.data.local.mapper.toEntity
import br.edu.ifpb.unipass.data.local.mapper.toTrip
import br.edu.ifpb.unipass.data.local.mapper.toUser
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val tripRepository: TripRepository,
    private val database: AppDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var tripListener: ListenerRegistration? = null
    private val currentUserId = "user123"

    init {
        loadUserData()
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
    private fun loadUserData() {
// 1. Carrega do Room primeiro (offline)
        viewModelScope.launch {
            database.tripDao().getNextTrip(currentUserId).collect { tripEntity ->
                _uiState.update { state ->
                    state.copy(
                        nextTrip = tripEntity?.toTrip(),
                        isLoading = false
                    )
                }
            }
        }

        // 2. Sincroniza com Firebase
        tripListener = tripRepository.observeNextTrip { trip ->
            viewModelScope.launch {
                trip?.let {
                    val tripEntity = it.toEntity(currentUserId)
                    database.tripDao().insertTrip(tripEntity)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        tripListener?.remove()
    }
}
