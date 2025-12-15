package br.edu.ifpb.unipass.models

data class Trip(
    val time: String,
    val origin: String,
    val destination: String,
    val seatNumber: String,
    val reservedSeats: Int,
    val totalSeats: Int
) {
    val progress: Float
        get() = reservedSeats.toFloat() / totalSeats.toFloat()

    val route: String
        get() = "$origin → $destination"
}
