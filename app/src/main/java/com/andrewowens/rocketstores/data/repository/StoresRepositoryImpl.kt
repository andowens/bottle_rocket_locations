package com.andrewowens.rocketstores.data.repository

import androidx.lifecycle.LiveData
import com.andrewowens.rocketstores.data.db.StoresDao
import com.andrewowens.rocketstores.data.db.entity.StoreEntry
import com.andrewowens.rocketstores.data.network.StoreNetworkDatasource
import com.andrewowens.rocketstores.data.network.response.StoresResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoresRepositoryImpl(
    private val storesDao: StoresDao,
    private val storeNetworkDatasource: StoreNetworkDatasource
) : StoresRepository {

    init {
        storeNetworkDatasource.apply {
            // Observe the live data
            downloadedStores.observeForever { newStoresData ->
                persistStores(newStoresData)
            }
        }
    }

    override suspend fun getStores(): LiveData<List<StoreEntry>> {
        return withContext(Dispatchers.IO) {
            initStoresData()
            // After the data is initiated we can just pull it from the Room repo
            return@withContext storesDao.getStores()
        }
    }

    override suspend fun getStoreDetailByStoreId(storeId: String): LiveData<StoreEntry> {
        return withContext(Dispatchers.IO) {
            initStoresData()
            return@withContext storesDao.getStoreDetailByStoreId(storeId)
        }
    }

    private fun persistStores(stores: StoresResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            val storesList = stores.storeEntries
            storesDao.upsert(storesList)
        }
    }

    /**
     * Helper function that just fetches the from the BottleRocketApi
     */
    private suspend fun initStoresData() {
        // Really there should be some sort of check here to make sure that we actually need to fetch data
        // but as this is just data that never changes I just init every time. This could be changed easily of course.
        storeNetworkDatasource.fetchStores()
    }
}