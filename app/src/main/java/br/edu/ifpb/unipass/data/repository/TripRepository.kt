package br.edu.ifpb.unipass.data.repository

import android.util.Log
import br.edu.ifpb.unipass.models.Trip
import br.edu.ifpb.unipass.models.TripStatus
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

class TripRepository(
    private val firestore: FirebaseFirestore
) {
    companion object {
        private const val TAG = "TripRepository"
    }

    suspend fun getScheduledTrips(): List<Trip> {
        return try {
            val snapshot = firestore.collection("trips")
                .whereEqualTo("status", TripStatus.SCHEDULED.value)
                .get()
                .await()

            val trips = snapshot.documents.mapNotNull { it.toTrip() }
            Log.d(TAG, "Viagens agendadas encontradas: ${trips.size}")
            trips.sortedBy { it.date }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar viagens agendadas: ${e.message}")
            emptyList()
        }
    }

    suspend fun getNextTrip(): Trip? {
        return try {
            val snapshot = firestore.collection("trips")
                .whereEqualTo("status", TripStatus.SCHEDULED.value)
                .get()
                .await()

            val trips = snapshot.documents.mapNotNull { it.toTrip() }
            val nextTrip = trips.sortedBy { it.date }.firstOrNull()
            Log.d(TAG, "Próxima viagem: ${nextTrip?.route ?: "nenhuma"}")
            nextTrip
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar próxima viagem: ${e.message}")
            null
        }
    }

    suspend fun getUserTripHistory(userId: String): List<Trip> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("trips")
                .get()
                .await()

            val trips = snapshot.documents.mapNotNull { it.toTrip() }
            Log.d(TAG, "Histórico de viagens do usuário $userId: ${trips.size} viagens")
            trips.sortedByDescending { it.date }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar histórico de viagens: ${e.message}")
            emptyList()
        }
    }

    fun observeNextTrip(onTripUpdate: String, function: () -> ERROR): ListenerRegistration {
        Log.d(TAG, "Iniciando listener de próxima viagem em tempo real")

        return firestore.collection("trips")
            .whereEqualTo("status", TripStatus.SCHEDULED.value)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Erro no listener de próxima viagem: ${error.message}")
                    onTripUpdate(null)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val trips = snapshot.documents.mapNotNull { it.toTrip() }
                    val nextTrip = trips.sortedBy { it.date }.firstOrNull()
                    Log.d(TAG, "Atualização em tempo real - Próxima viagem: ${nextTrip?.route ?: "nenhuma"}")
                    onTripUpdate(nextTrip)
                }
            }
    }

    fun observeUserTripHistory(userId: String, onTripsUpdate: (List<Trip>) -> Unit): ListenerRegistration {
        Log.d(TAG, "Iniciando listener de histórico em tempo real para usuário: $userId")

        return firestore.collection("users")
            .document(userId)
            .collection("trips")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Erro no listener de histórico: ${error.message}")
                    onTripsUpdate(emptyList())
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val trips = snapshot.documents.mapNotNull { it.toTrip() }
                    Log.d(TAG, "Atualização em tempo real - Histórico: ${trips.size} viagens")
                    onTripsUpdate(trips.sortedByDescending { it.date })
                }
            }
    }

    private fun DocumentSnapshot.toTrip(): Trip? {
        return try {
            Trip(
                id = id,
                date = getString("date") ?: "",
                time = getString("time") ?: "",
                origin = getString("origin") ?: "",
                destination = getString("destination") ?: "",
                seatNumber = getString("seatNumber") ?: "",
                status = TripStatus.fromValue(getString("status") ?: ""),
                reservedSeats = getLong("reservedSeats")?.toInt() ?: 0,
                totalSeats = getLong("totalSeats")?.toInt() ?: 0
            )
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao converter documento ${id}: ${e.message}")
            null
        }
    }
}
