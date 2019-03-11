package com.andrewowens.rocketstores.ui.list

import androidx.lifecycle.ViewModel;
import com.andrewowens.rocketstores.data.repository.StoresRepository
import com.andrewowens.rocketstores.internal.lazyDeferred

class StoresListViewModel(
    private val storesRepository: StoresRepository
) : ViewModel() {

    val storeEntries by lazyDeferred {
        storesRepository.getStores()
    }
}
