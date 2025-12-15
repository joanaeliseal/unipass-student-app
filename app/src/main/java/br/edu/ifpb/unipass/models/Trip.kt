package br.edu.ifpb.unipass.models

/**
 * Modelo unificado de Viagem
 * Usado tanto para próximas viagens quanto para histórico
 */
data class Trip(
    val id: String = "",
    val date: String = "",                  // Formato: "14/12/2024"
    val time: String = "",                  // Formato: "07:30"
    val origin: String = "",
    val destination: String = "",
    val seatNumber: String = "",
    val status: String = "SCHEDULED",       // SCHEDULED, COMPLETED, CANCELLED, NO_SHOW
    val reservedSeats: Int = 0,             // Usado apenas em viagens futuras
    val totalSeats: Int = 0                 // Usado apenas em viagens futuras
) {
    constructor() : this("", "", "", "", "", "", "SCHEDULED", 0, 0)

    val route: String
        get() = "$origin → $destination"

    val dateTime: String
        get() = "$date às $time"

    val progress: Float
        get() = if (totalSeats > 0) reservedSeats.toFloat() / totalSeats.toFloat() else 0f

    val isCompleted: Boolean
        get() = status == "COMPLETED"

    val isCancelled: Boolean
        get() = status == "CANCELLED" || status == "NO_SHOW"
}

enum class TripStatus {
    SCHEDULED,      // Viagem agendada (próxima viagem)
    COMPLETED,      // Viagem concluída
    CANCELLED,      // Viagem cancelada
    NO_SHOW         // Não compareceu
}
