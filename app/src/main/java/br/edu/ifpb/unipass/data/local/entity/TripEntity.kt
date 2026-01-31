package br.edu.ifpb.unipass.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade Room para armazenar viagens localmente
 * Corresponde ao model Trip
 */

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey
    val id: String,
    val date: String,
    val time: String,
    val origin: String,
    val destination: String,
    val seatNumber: String,
    val status: String, // Armazenado como String para compatibilidade com Room
    val reservedSeats: Int,
    val totalSeats: Int,
    val userId: String, // FK para relacionar com o usuário
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)