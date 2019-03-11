package com.andrewowens.rocketstores.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andrewowens.rocketstores.data.network.response.StoresResponse
import com.andrewowens.rocketstores.internal.NoConnectivityException

class StoreNetworkDatasourceImpl(
    private val bottleRocketApiService: BottleRocketApiService
) : StoreNetworkDatasource {

    private val _downloadedStores = MutableLiveData<StoresResponse>()

    override val downloadedStores: LiveData<StoresResponse>
        get() = _downloadedStores

    override suspend fun fetchStores() {
        try {
            val fetchedStores = bottleRocketApiService.getStores().await()
            _downloadedStores.postValue(fetchedStores)
        } catch (e: NoConnectivityException) {

        }
    }
}