package br.edu.ifpb.unipass.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade Room para armazenar dados do usuário localmente
 * Corresponde ao model User
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val cpf: String,
    val email: String = "",
    val photoUrl: String? = null,
    val notificationCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    /**
     * Calcula as iniciais do usuário automaticamente
     */
    val initials: String
        get() = name.split(" ")
            .take(2)
            .mapNotNull { it.firstOrNull()?.uppercase() }
            .joinToString("")
}