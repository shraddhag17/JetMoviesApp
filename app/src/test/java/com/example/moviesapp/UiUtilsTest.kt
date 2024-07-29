package com.example.moviesapp

import com.example.moviesapp.common.utils.UiUtils
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UiUtilsTest {

    @Test
    fun test_formatAverageVote() {
        var result = UiUtils.formatAverageVote(null)
        assertEquals(0.00, result)
        result = UiUtils.formatAverageVote(0.0)
        assertEquals(0.00, result)
        result = UiUtils.formatAverageVote(12.7)
        assertEquals(12.70, result)
        result = UiUtils.formatAverageVote(12.6666)
        assertEquals(12.67, result)
    }

    @Test
    fun test_formatDuration() {
        var result = UiUtils.formatDuration(null)
        assertEquals("", result)
        result = UiUtils.formatDuration("")
        assertEquals("", result)
        result = UiUtils.formatDuration("120")
        assertEquals("2h", result)
        result = UiUtils.formatDuration("220")
        assertEquals("3h40m", result)
    }
}