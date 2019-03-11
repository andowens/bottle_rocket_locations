package com.andrewowens.rocketstores.data.network

import androidx.lifecycle.LiveData
import com.andrewowens.rocketstores.data.network.response.StoresResponse

interface StoreNetworkDatasource {
    val downloadedStores: LiveData<StoresResponse>

    suspend fun fetchStores()
}