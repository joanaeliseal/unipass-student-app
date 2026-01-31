package br.edu.ifpb.unipass.data.local.dao

import androidx.room.*
import br.edu.ifpb.unipass.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE cpf = :cpf")
    suspend fun getUserByCpf(cpf: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("UPDATE users SET notificationCount = :count WHERE id = :userId")
    suspend fun updateNotificationCount(userId: String, count: Int)
}