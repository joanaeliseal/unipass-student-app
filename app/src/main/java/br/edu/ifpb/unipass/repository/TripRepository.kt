package br.edu.ifpb.unipass.repository

import android.util.Log
import br.edu.ifpb.unipass.models.Trip
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

/**
 * Repository para operações de viagens no Firestore
 */
class TripRepository {
    private val firestore: FirebaseFirestore

    companion object {
        private const val TAG = "TripRepository"
    }

    init {
        firestore = FirebaseFirestore.getInstance()
        Log.d(TAG, "FirebaseFirestore inicializado: ${firestore.app.name}")
    }


    /**
     * Busca todas as viagens agendadas (próximas viagens)
     * Coleção: trips
     * Filtra por status = "SCHEDULED"
     */
    suspend fun getScheduledTrips(): List<Trip> {
        return try {
            val snapshot = firestore.collection("trips")
                .whereEqualTo("status", "SCHEDULED")
                .get()
                .await()

            val trips = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Trip::class.java)?.copy(id = doc.id)
            }

            Log.d(TAG, "Viagens agendadas encontradas: ${trips.size}")
            trips.sortedBy { it.date }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar viagens agendadas: ${e.message}")
            emptyList()
        }
    }

    /**
     * Busca a próxima viagem agendada do usuário
     * Retorna a primeira viagem com status SCHEDULED
     */
    suspend fun getNextTrip(): Trip? {
        return try {
            val snapshot = firestore.collection("trips")
                .whereEqualTo("status", "SCHEDULED")
                .get()
                .await()

            val trips = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Trip::class.java)?.copy(id = doc.id)
            }

            val nextTrip = trips.sortedBy { it.date }.firstOrNull()
            Log.d(TAG, "Próxima viagem: ${nextTrip?.route ?: "nenhuma"}")
            nextTrip
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar próxima viagem: ${e.message}")
            null
        }
    }

    /**
     * Busca o histórico de viagens de um usuário
     * Coleção: users/{userId}/trips
     */
    suspend fun getUserTripHistory(userId: String): List<Trip> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("trips")
                .get()
                .await()

            val trips = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Trip::class.java)?.copy(id = doc.id)
            }

            Log.d(TAG, "Histórico de viagens do usuário $userId: ${trips.size} viagens")
            trips.sortedByDescending { it.date }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar histórico de viagens: ${e.message}")
            emptyList()
        }
    }

    /**
     * Filtra viagens por status
     */
    fun filterTripsByStatus(trips: List<Trip>, status: String): List<Trip> {
        return when (status) {
            "ALL" -> trips
            "COMPLETED" -> trips.filter { it.status == "COMPLETED" }
            "CANCELLED" -> trips.filter { it.status == "CANCELLED" || it.status == "NO_SHOW" }
            else -> trips
        }
    }
}
