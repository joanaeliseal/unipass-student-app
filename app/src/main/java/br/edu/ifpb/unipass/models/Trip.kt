package br.edu.ifpb.unipass.models

enum class TripStatus(val value: String) {
    SCHEDULED("SCHEDULED"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    NO_SHOW("NO_SHOW");

    companion object {
        fun fromValue(value: String): TripStatus =
            entries.find { it.value == value } ?: SCHEDULED
    }
}

data class Trip(
    val id: String = "",
    val date: String = "",
    val time: String = "",
    val origin: String = "",
    val destination: String = "",
    val seatNumber: String = "",
    val status: TripStatus = TripStatus.SCHEDULED,
    val reservedSeats: Int = 0,
    val totalSeats: Int = 0
) {
    val route: String
        get() = "$origin → $destination"

    val dateTime: String
        get() = "$date às $time"

    val progress: Float
        get() = if (totalSeats > 0) reservedSeats.toFloat() / totalSeats.toFloat() else 0f

    val isCompleted: Boolean
        get() = status == TripStatus.COMPLETED

    val isCancelled: Boolean
        get() = status == TripStatus.CANCELLED || status == TripStatus.NO_SHOW
}
