package com.example.moviesapp

import com.example.moviesapp.common.utils.DateUtils
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Calendar

class DateUtilsTest {

    @Test
    fun test_convertDateToString() {
        val calendar = Calendar.getInstance()
        calendar.set(2024, 1, 4)
        var result = DateUtils.convertDateToString(calendar.time, "MMM dd, yyyy")
        assertEquals("Feb 04, 2024", result)
        result = DateUtils.convertDateToString(null, "MMM dd, yyyy")
        assertEquals("", result)
    }

    @Test
    fun test_convertStringToDate() {
        val calendar = Calendar.getInstance()
        calendar.time = DateUtils.convertStringToDate("2024-01-04", "yyyy-MM-dd")!!
        assertEquals(4, calendar.get(Calendar.DATE))
        val result = DateUtils.convertStringToDate(null, "yyyy-MM-dd")
        assertEquals(null, result)
    }
}