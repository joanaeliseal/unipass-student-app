package br.edu.ifpb.unipass.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade Room para armazenar carteirinha do estudante localmente
 * Corresponde ao model StudentCard
 */
@Entity(tableName = "student_cards")
data class StudentCardEntity(
    @PrimaryKey
    val id: String,
    val userId: String, // FK para relacionar com o usuário
    val studentName: String,
    val institution: String,
    val course: String,
    val shift: String,
    val cardNumber: String,
    val isActive: Boolean,
    val validUntil: String,
    val photoUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)