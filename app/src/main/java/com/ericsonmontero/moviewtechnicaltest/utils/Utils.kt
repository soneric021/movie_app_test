package com.ericsonmontero.moviewtechnicaltest.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun parseDate(dateString:String): Date? {
        return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).parse(dateString)

    }

}