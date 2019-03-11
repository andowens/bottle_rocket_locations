package com.andrewowens.rocketstores.data.network

import com.andrewowens.rocketstores.data.network.response.StoresResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json

interface BottleRocketApiService {

    /***
     * Deferred function that will be used to get the stores data from the api.
     */
    @GET("stores.json")
    fun getStores() : Deferred<StoresResponse>

    companion object {

        /**
         * Using invoke so that instead of having to do something like BottleRocketApiService.create()
         * to create the api we can do BottleRocketApiService()
         */
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): BottleRocketApiService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BottleRocketApiService::class.java)
        }
    }
}