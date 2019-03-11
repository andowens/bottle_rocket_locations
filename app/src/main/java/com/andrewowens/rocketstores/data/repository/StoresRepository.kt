package com.andrewowens.rocketstores.data.repository

import androidx.lifecycle.LiveData
import com.andrewowens.rocketstores.data.db.entity.StoreEntry

interface StoresRepository {

    suspend fun getStores() : LiveData<List<StoreEntry>>

    suspend fun getStoreDetailByStoreId(storeId: String) : LiveData<StoreEntry>
}