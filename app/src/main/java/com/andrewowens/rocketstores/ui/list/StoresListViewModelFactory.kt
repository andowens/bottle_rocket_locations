package com.andrewowens.rocketstores.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andrewowens.rocketstores.data.repository.StoresRepository

/**
 * Need this for dependency injection
 */
class StoresListViewModelFactory(
    private val storesRepository: StoresRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StoresListViewModel(storesRepository) as T
    }
}