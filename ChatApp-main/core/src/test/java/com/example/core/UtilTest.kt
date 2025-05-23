package com.example.core

import junit.framework.TestCase.assertEquals
import org.junit.Test

class UtilTest {

    @Test
    fun formatPhoneNumber_testUSPhoneNumber() {
        val input = "+11234567890"
        val expected = "+1 123 456 7890"
        val actual = formatPhoneNumber(input)
        assertEquals(expected, actual)
    }

    @Test
    fun formatPhoneNumber_testUKPhoneNumber() {
        val input = "+441234567890"
        val expected = "+44 123 456 7890"
        val actual = formatPhoneNumber(input)
        assertEquals(expected, actual)
    }

    @Test
    fun formatPhoneNumber_testPhoneNumberWithSpaces() {
        // Even if the input contains spaces, the function cleans them out first.
        val input = "+1 1234567890"
        val expected = "+1 123 456 7890"
        val actual = formatPhoneNumber(input)
        assertEquals(expected, actual)
    }

    @Test
    fun formatPhoneNumber_testTooShortPhoneNumber() {
        // If the input is too short (e.g., not enough digits), the original string is returned.
        val input = "+123456789"
        val actual = formatPhoneNumber(input)
        assertEquals(input, actual)
    }

    @Test
    fun formatPhoneNumber_testPhoneNumberWithoutPlus() {
        // If the phone number does not start with '+', the original string is returned.
        val input = "1234567890"
        val actual = formatPhoneNumber(input)
        assertEquals(input, actual)
    }

}