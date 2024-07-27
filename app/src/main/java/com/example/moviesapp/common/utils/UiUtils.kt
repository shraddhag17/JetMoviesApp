package com.example.moviesapp.common.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Locale
import java.util.function.DoubleToLongFunction
import kotlin.math.abs

object UiUtils {
    fun formatAverageVote(vote : Double?) : Double {
        vote?.let {
            try {
                val format = BigDecimal(vote).setScale(2, RoundingMode.HALF_UP)
                //val format = String.format(Locale.getDefault(), "%.2f", vote.toString())
                return format.toDouble()
            }catch (_: Exception) {

            }
        }
        return vote ?: 0.0
    }
}