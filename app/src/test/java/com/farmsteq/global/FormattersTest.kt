package com.farmsteq.global

import com.farmsteq.global.util.greetingForHour
import com.farmsteq.global.util.initials
import kotlin.test.Test
import kotlin.test.assertEquals

class FormattersTest {
    @Test
    fun greetingsFollowLocalTime() {
        assertEquals("Good morning", greetingForHour(7))
        assertEquals("Good afternoon", greetingForHour(14))
        assertEquals("Good evening", greetingForHour(19))
        assertEquals("Good night", greetingForHour(22))
    }

    @Test
    fun initialsHandleNamesAndBlanks() {
        assertEquals("SN", initials("Sanda Nathan"))
        assertEquals("FG", initials("  "))
    }
}
