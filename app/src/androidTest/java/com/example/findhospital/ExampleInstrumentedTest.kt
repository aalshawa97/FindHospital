package com.example.findhospital

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.findhospital", appContext.packageName)
    }
    @Test
    fun givenTwoStrings_concatenateWithPlusOperator_thenEquals() {
        val a = "Hello"
        val b = "Baeldung"
        val c = a + " " + b

        assertEquals("Hello Baeldung", c)
    }
}