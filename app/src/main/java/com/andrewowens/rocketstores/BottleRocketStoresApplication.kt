package com.andrewowens.rocketstores

import android.app.Application
import com.andrewowens.rocketstores.data.db.StoresDatabase
import com.andrewowens.rocketstores.data.network.*
import com.andrewowens.rocketstores.data.repository.StoresRepository
import com.andrewowens.rocketstores.data.repository.StoresRepositoryImpl
import com.andrewowens.rocketstores.ui.detail.StoresDetailViewModelFactory
import com.andrewowens.rocketstores.ui.list.StoresListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class BottleRocketStoresApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@BottleRocketStoresApplication))

        bind() from singleton { StoresDatabase(instance()) }
        bind() from singleton { instance<StoresDatabase>().storesDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { BottleRocketApiService(instance()) }
        bind<StoreNetworkDatasource>() with singleton { StoreNetworkDatasourceImpl(instance()) }
        bind<StoresRepository>() with singleton { StoresRepositoryImpl(instance(), instance()) }
        bind() from provider { StoresListViewModelFactory(instance()) }
        bind() from factory { storeId: String -> StoresDetailViewModelFactory(storeId, instance()) }
    }
}