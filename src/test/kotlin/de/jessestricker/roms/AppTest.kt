package de.jessestricker.roms

import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class AppTest {
    @Test
    fun appHasAGreeting() {
        val classUnderTest = App()
        classUnderTest.greeting shouldNotBe null
    }
}
