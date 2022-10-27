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

    fun hoursFromMinuts(minutes:Int): Int {
        return minutes / 60
    }

}