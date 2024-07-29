package com.example.moviesapp.common.utils

import java.math.BigDecimal
import java.math.RoundingMode

object UiUtils {
    fun formatAverageVote(vote: Double?): Double {
        vote?.let {
            try {
                val format = BigDecimal(vote).setScale(2, RoundingMode.HALF_UP)
                //val format = String.format(Locale.getDefault(), "%.2f", vote.toString())
                return format.toDouble()
            } catch (_: Exception) {

            }
        }
        return vote ?: 0.00
    }

    fun formatDuration(minutes: String?): String {
        val totalMinutes = minutes?.toIntOrNull() ?: return ""

        // Calculate hours and remaining minutes
        val hours = totalMinutes / 60
        val remainingMinutes = totalMinutes % 60
        val builder = StringBuilder()

        // Create a formatted string
        if (hours > 0) {
            builder.append(hours).append("h")
            if (remainingMinutes > 0) {
                builder.append(remainingMinutes).append("m").toString()
            }
        } else {
            builder.append(remainingMinutes).append("m")
        }
        return builder.toString()
    }
}