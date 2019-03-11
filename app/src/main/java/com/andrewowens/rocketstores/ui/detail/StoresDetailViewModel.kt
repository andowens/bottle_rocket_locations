package com.andrewowens.rocketstores.ui.detail

import androidx.lifecycle.ViewModel;
import com.andrewowens.rocketstores.data.repository.StoresRepository
import com.andrewowens.rocketstores.internal.lazyDeferred

class StoresDetailViewModel(
    private val storeId: String,
    private val storesRepository: StoresRepository
) : ViewModel() {

    val store by lazyDeferred {
        storesRepository.getStoreDetailByStoreId(storeId)
    }
}
