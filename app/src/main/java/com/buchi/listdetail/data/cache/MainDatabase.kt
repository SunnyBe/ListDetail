package com.buchi.listdetail.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.buchi.listdetail.data.model.MainEntity
import com.buchi.listdetail.utils.Converters
import kotlinx.coroutines.CoroutineScope

@Database(entities = [MainEntity.User::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MainDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "users-db"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}