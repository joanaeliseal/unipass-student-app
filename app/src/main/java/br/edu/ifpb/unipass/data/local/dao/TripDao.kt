package br.edu.ifpb.unipass.data.local.dao

import androidx.room.*
import br.edu.ifpb.unipass.data.local.entity.TripEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("SELECT * FROM trips WHERE userId = :userId ORDER BY date DESC, time DESC")
    fun getAllTrips(userId: String): Flow<List<TripEntity>>

    @Query("SELECT * FROM trips WHERE userId = :userId AND status = :status ORDER BY date DESC, time DESC")
    fun getTripsByStatus(userId: String, status: String): Flow<List<TripEntity>>

    @Query("SELECT * FROM trips WHERE userId = :userId AND status = 'SCHEDULED' ORDER BY date ASC, time ASC LIMIT 1")
    fun getNextTrip(userId: String): Flow<TripEntity?>

    @Query("SELECT * FROM trips WHERE id = :tripId")
    suspend fun getTripById(tripId: String): TripEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: TripEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrips(trips: List<TripEntity>)

    @Update
    suspend fun updateTrip(trip: TripEntity)

    @Delete
    suspend fun deleteTrip(trip: TripEntity)

    @Query("DELETE FROM trips WHERE userId = :userId")
    suspend fun deleteAllTrips(userId: String)

    @Query("DELETE FROM trips WHERE id = :tripId")
    suspend fun deleteTripById(tripId: String)

    @Query("SELECT COUNT(*) FROM trips WHERE userId = :userId AND status = 'SCHEDULED'")
    suspend fun getScheduledTripsCount(userId: String): Int
}