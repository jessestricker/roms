package de.jessestricker.roms

import com.github.ajalt.clikt.core.CliktCommand

class App : CliktCommand() {
    val greeting: String get() = "Hello World!"

    override fun run() {
        println(greeting)
    }
}

fun main(args: Array<String>) {
    App().main(args)
}
