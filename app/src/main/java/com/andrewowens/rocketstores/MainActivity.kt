package com.andrewowens.rocketstores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.andrewowens.rocketstores.data.network.BottleRocketApi
import com.andrewowens.rocketstores.data.network.ConnectivityInterceptor
import com.andrewowens.rocketstores.data.network.ConnectivityInterceptorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService = BottleRocketApi(ConnectivityInterceptorImpl(applicationContext))

        GlobalScope.launch(Dispatchers.Main) {
            val stores = apiService.getStores().await()

            Log.d("TAG", "Stores $stores)")
        }
    }
}
