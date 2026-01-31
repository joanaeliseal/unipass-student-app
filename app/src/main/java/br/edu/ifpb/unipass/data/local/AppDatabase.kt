package br.edu.ifpb.unipass.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.ifpb.unipass.data.local.dao.StudentCardDao
import br.edu.ifpb.unipass.data.local.dao.TripDao
import br.edu.ifpb.unipass.data.local.dao.UserDao
import br.edu.ifpb.unipass.data.local.entity.StudentCardEntity
import br.edu.ifpb.unipass.data.local.entity.TripEntity
import br.edu.ifpb.unipass.data.local.entity.UserEntity

@Database(
    entities = [
        TripEntity::class,
        UserEntity::class,
        StudentCardEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun userDao(): UserDao
    abstract fun studentCardDao(): StudentCardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "unipass_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}