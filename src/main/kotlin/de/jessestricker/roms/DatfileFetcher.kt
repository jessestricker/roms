package de.jessestricker.roms

import org.jetbrains.annotations.TestOnly
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.file.Path
import java.util.zip.ZipInputStream
import kotlin.io.path.outputStream

enum class Console {
    NINTENDO_GAMECUBE,
    NINTENDO_WII,
}

interface DatfileFetcher {
    fun fetch(console: Console, datfilePath: Path)
}

class HttpNotOkStatusException(statusCode: Int) :
    RuntimeException("The HTTP response returned with a status code of $statusCode.")

class EmptyZipFileException : RuntimeException("Tried to decompress an entry from an empty ZIP file.")

class RedumpDatfileFetcher : DatfileFetcher {
    private val baseUri: URI

    @TestOnly
    internal constructor(baseUri: URI) {
        this.baseUri = baseUri
    }

    constructor() : this(URI(DEFAULT_BASE_URI))

    private val httpClient: HttpClient = HttpClient.newHttpClient()

    override fun fetch(console: Console, datfilePath: Path) {
        val subUri = CONSOLE_URI_PATHS[console] ?: throw IllegalArgumentException("unsupported console: $console")
        val uri = baseUri.resolve(subUri)
        val request = HttpRequest.newBuilder(uri).header(HttpHeader.ACCEPT, MediaType.APPLICATION_ZIP).build()
        val response = httpClient.send(request, BodyHandlers.ofInputStream())
        if (response.statusCode() != HttpStatus.OK) {
            throw HttpNotOkStatusException(response.statusCode())
        }
        ZipInputStream(response.body()).use { zipStream ->
            zipStream.nextEntry ?: throw EmptyZipFileException()
            datfilePath.outputStream().use { datfileStream ->
                zipStream.copyTo(datfileStream)
            }
        }
    }

    companion object {
        private const val DEFAULT_BASE_URI = "http://redump.org/datfile/"
        private val CONSOLE_URI_PATHS = mapOf(
            Console.NINTENDO_GAMECUBE to "gc",
            Console.NINTENDO_WII to "wii",
        )
    }
}

private object HttpHeader {
    const val ACCEPT = "Accept"
}

private object MediaType {
    const val APPLICATION_ZIP = "application/zip"
}

private object HttpStatus {
    const val OK = 200
}
