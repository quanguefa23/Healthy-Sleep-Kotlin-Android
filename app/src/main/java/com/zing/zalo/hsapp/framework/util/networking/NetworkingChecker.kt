package com.zing.zalo.hsapp.framework.util.networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import timber.log.Timber
import java.io.IOException


/**
 * Support class to check networking and internet
 */
object NetworkingChecker {

    // check if we have an internet connection
    fun isInternetAvailable(context: Context): Boolean {
        if (!isNetworkConnected(context)) return false

        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }

    // check if device is connected to any network (ex: wifi)
    fun isNetworkConnected(context: Context): Boolean {
        Timber.i("start check")
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }.also {
            Timber.i("stop check")
        }
    }
}