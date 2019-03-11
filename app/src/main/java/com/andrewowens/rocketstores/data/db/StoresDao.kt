package com.andrewowens.rocketstores.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andrewowens.rocketstores.data.db.entity.StoreEntry

@Dao
interface StoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(storeEntry: List<StoreEntry>)

    @Query("select * from stores")
    fun getStores(): LiveData<List<StoreEntry>>

    @Query("select count(id) from stores")
    fun countStores(): Int

    @Query("select * from stores where storeID = :storeId")
    fun getStoreDetailByStoreId(storeId: String) : LiveData<StoreEntry>

    @Query("delete from stores")
    fun deleteOldStores()
}