package de.jessestricker.roms

import de.jessestricker.roms.datfile.Datfile
import de.jessestricker.roms.datfile.Game
import de.jessestricker.roms.datfile.Header
import de.jessestricker.roms.datfile.Rom
import de.jessestricker.roms.datfile.toSha1Digest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.File

class DatfileTest {
    @Test
    fun `parse datfile`() {
        val datfile = Datfile.load(File("src/test/resources/datfile.xml"))

        datfile shouldBe Datfile(
            header = Header(name = "The name", version = "The version"),
            games = setOf(
                Game(
                    name = "The game 1",
                    roms = setOf(
                        Rom(
                            name = "The rom 1.iso",
                            size = 12345,
                            sha1 = "75D5C116AB1638B1387488B21D13CE2F150C4454".toSha1Digest()
                        )
                    )
                ),
                Game(
                    name = "The game 2",
                    roms = setOf(
                        Rom(
                            name = "The rom 2.iso",
                            size = 54321,
                            sha1 = "F1FD3D0ADECAFC52C94B047021B1874B75D050FF".toSha1Digest()
                        ),
                        Rom(
                            name = "The rom 3.iso",
                            size = 21543,
                            sha1 = "659AFF3D1BC508E4ABBA424BB57463B51AA8DA93".toSha1Digest()
                        )
                    )
                )
            )
        )
    }
}
