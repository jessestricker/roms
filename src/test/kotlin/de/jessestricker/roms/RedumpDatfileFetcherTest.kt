package de.jessestricker.roms

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
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
    fun `fetch Nintendo Wii datfile`(@TempDir tempDir: Path, server: MockWebServer) {
        val expectedDatfileBuffer = Buffer()
        FileSystem.SYSTEM.read("src/test/resources/datfile.xml".toPath()) { readAll(expectedDatfileBuffer) }
        val zippedDatfileBuffer = Buffer()
        FileSystem.SYSTEM.read("src/test/resources/datfile.xml.zip".toPath()) { readAll(zippedDatfileBuffer) }
        server.enqueue(MockResponse.Builder().body(zippedDatfileBuffer).build())

        val datfilePath = tempDir / "wii.xml"
        val fetcher = RedumpDatfileFetcher(server.url("/").toUri())
        fetcher.fetch(Console.NINTENDO_WII, datfilePath)

        server.takeRequest().requestUrl shouldBe server.url("/wii")

        val datfileBuffer = Buffer()
        FileSystem.SYSTEM.read(datfilePath.toOkioPath()) { readAll(datfileBuffer) }
        datfileBuffer shouldBeEqual expectedDatfileBuffer
    }

    @Test
    fun `fetch Nintendo Wii datfile - 404`(@TempDir tempDir: Path, server: MockWebServer) {
        server.enqueue(MockResponse(code = 404))

        val datfilePath = tempDir / "wii.xml"
        val fetcher = RedumpDatfileFetcher(server.url("/").toUri())
        shouldThrow<HttpNotOkStatusException> { fetcher.fetch(Console.NINTENDO_WII, datfilePath) }

        server.takeRequest().requestUrl shouldBe server.url("/wii")
    }

    @Test
    fun `fetch Nintendo Wii datfile - empty zip`(@TempDir tempDir: Path, server: MockWebServer) {
        val emptyZipBuffer = Buffer()
        FileSystem.SYSTEM.read("src/test/resources/empty.zip".toPath()) { readAll(emptyZipBuffer) }
        server.enqueue(MockResponse.Builder().body(emptyZipBuffer).build())

        val datfilePath = tempDir / "wii.xml"
        val fetcher = RedumpDatfileFetcher(server.url("/").toUri())
        shouldThrow<EmptyZipFileException> { fetcher.fetch(Console.NINTENDO_WII, datfilePath) }

        server.takeRequest().requestUrl shouldBe server.url("/wii")
    }
}
