package com.example.moviesapp.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun convertStringToDate(input : String?, inputFormat : String = "yyyy-mm-dd") : Date? {
        input?.let {
            try{
                val format = SimpleDateFormat(inputFormat, Locale.getDefault())
                return format.parse(input)
            } catch (exception : Exception) {
                //Report to firebase
            }
        }
        return null
    }

    fun convertDateToString(input : Date?, outputFormat : String = "MMM dd, yyyy") : String {
        input?.let {
            try{
                val format = SimpleDateFormat(outputFormat, Locale.getDefault())
                return format.format(input)
            } catch (exception : Exception) {
                //Report to firebase
            }
        }
        return ""
    }
}