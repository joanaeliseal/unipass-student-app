package br.edu.ifpb.unipass.ui.state

import br.edu.ifpb.unipass.models.Trip
import br.edu.ifpb.unipass.models.User
import br.edu.ifpb.unipass.models.StudentCard

/**
 * UI State para a tela Home
 */
data class HomeUiState(
    val user: User? = null,
    val nextTrip: Trip? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * UI State para a tela de Viagens
 */
data class TripsUiState(
    val trips: List<Trip> = emptyList(),
    val selectedFilter: TripFilter = TripFilter.ALL,
    val isLoading: Boolean = true,
    val error: String? = null
)

enum class TripFilter {
    ALL, COMPLETED, CANCELLED
}

/**
 * UI State para a tela de Carteirinha
 */
data class StudentCardUiState(
    val studentCard: StudentCard? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * UI State para a tela de Login
 */
data class LoginUiState(
    val cpf: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPasswordVisible: Boolean = false
)

/**
 * UI State para a tela de Perfil
 */
data class ProfileUiState(
    val user: User? = null,
    val studentCard: StudentCard? = null,
    val scheduledTripsCount: Int = 0,
    val completedTripsCount: Int = 0,
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * UI State para a tela de Reserva
 */
data class BookingUiState(
    val availableTrips: List<Trip> = emptyList(),
    val selectedTrip: Trip? = null,
    val selectedSeat: String? = null,
    val isLoading: Boolean = true,
    val isBooking: Boolean = false,
    val error: String? = null,
    val bookingSuccess: Boolean = false
)