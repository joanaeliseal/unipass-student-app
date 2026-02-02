package br.edu.ifpb.unipass.data.local.dao

import androidx.room.*
import br.edu.ifpb.unipass.data.local.entity.StudentCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentCardDao {
    @Query("SELECT * FROM student_cards WHERE userId = :userId")
    fun getStudentCardByUserId(userId: String): Flow<StudentCardEntity?>

    @Query("SELECT * FROM student_cards WHERE id = :cardId")
    suspend fun getStudentCardById(cardId: String): StudentCardEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentCard(card: StudentCardEntity)

    @Update
    suspend fun updateStudentCard(card: StudentCardEntity)

    @Delete
    suspend fun deleteStudentCard(card: StudentCardEntity)

    @Query("UPDATE student_cards SET isActive = :isActive WHERE id = :cardId")
    suspend fun updateCardStatus(cardId: String, isActive: Boolean)
}


