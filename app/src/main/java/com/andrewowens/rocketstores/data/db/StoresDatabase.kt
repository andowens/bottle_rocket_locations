package com.andrewowens.rocketstores.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andrewowens.rocketstores.data.db.entity.StoreEntry

// Using room for local storage
@Database(entities = [StoreEntry::class], version = 1)
abstract class StoresDatabase : RoomDatabase() {

    abstract fun storesDao(): StoresDao

    companion object {
        @Volatile private var instance: StoresDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildStoresDatabase(context).also { instance = it }
        }

        private fun buildStoresDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            StoresDatabase::class.java,
            "storesEntry.db").build()
    }
}