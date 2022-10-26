package com.ericsonmontero.moviewtechnicaltest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun parseDate(dateString:String): Date? {
        return try {
             SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).parse(dateString)
        }catch (ex:ParseException){
            ex.printStackTrace()
            null
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnected(context: Context): Boolean {
        //1
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //2
        val activeNetwork = connectivityManager.activeNetwork
        //3
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        //4
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}