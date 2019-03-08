package com.andrewowens.rocketstores.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.andrewowens.rocketstores.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Class that that is an interceptor that is passed to Retrofit it is used to check if the device is
 * connected to the internet
 */
class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {

    // Use the application context as it is around as long as the application is
    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        // Simply check if the device is online if not throw an exception
        if (!isOnline()) {
            throw NoConnectivityException()
        }

        return chain.proceed(chain.request())
    }

    /**
     * Helper function that checks if the devices is online.
     * @return True if it is online false if not
     */
    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}