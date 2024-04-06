package de.jessestricker.roms

import io.kotest.matchers.equals.shouldBeEqual
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.junit5.internal.MockWebServerExtension
import okio.Buffer
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import okio.Path.Companion.toPath
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.div

@ExtendWith(MockWebServerExtension::class)
class RedumpDatfileFetcherTest {
    @Test
    fun `fetch Nintendo Wii datfile from redump`(@TempDir tempDir: Path, server: MockWebServer) {
        val expectedDatfileBuffer = Buffer()
        FileSystem.SYSTEM.read("src/test/resources/redump_wii.dat".toPath()) { readAll(expectedDatfileBuffer) }
        val zippedDatfileBuffer = Buffer()
        FileSystem.SYSTEM.read("src/test/resources/redump_wii.dat.zip".toPath()) { readAll(zippedDatfileBuffer) }
        server.enqueue(MockResponse.Builder().body(zippedDatfileBuffer).build())

        val datfilePath = tempDir / "wii.xml"
        val fetcher = RedumpDatfileFetcher(server.url("/").toUri())
        fetcher.fetch(Console.NINTENDO_WII, datfilePath)

        val datfileBuffer = Buffer()
        FileSystem.SYSTEM.read(datfilePath.toOkioPath()) { readAll(datfileBuffer) }
        datfileBuffer shouldBeEqual expectedDatfileBuffer
    }
}
