package de.jessestricker.roms

import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.io.File
import kotlin.test.Test

class DatfileTest {
    @Test
    fun `parse datfile`() {
        val datfile = Datfile.load(File("src/test/resources/redump_wii.dat"))

        datfile.header shouldBe Header(
            name = "Nintendo - Wii",
            version = "2024-04-02 02-34-39",
        )
        datfile.games shouldHaveSize 3770
    }

    @Test
    fun `parse header`() {
        val header = xmlMapper.readValue<Header>(
            """
            <header>
                <name>Nintendo - Wii</name>
                <description>Nintendo - Wii - Discs (3770) (2024-04-02 02-34-39)</description>
                <version>2024-04-02 02-34-39</version>
                <date>2024-04-02 02-34-39</date>
                <author>redump.org</author>
                <homepage>redump.org</homepage>
                <url>http://redump.org/</url>
            </header>
            """
        )

        header shouldBe Header(
            name = "Nintendo - Wii",
            version = "2024-04-02 02-34-39",
        )
    }

    @Test
    fun `parse game`() {
        val game = xmlMapper.readValue<Game>(
            """
            <game name="Metal Slug Anthology (USA)">
                <category>Games</category>
                <description>Metal Slug Anthology (USA)</description>
                <rom name="Metal Slug Anthology (USA).iso" size="4699979776" crc="68ed804e" md5="16d7bab53fa02d4ddd61b47da66cef93" sha1="9c793e1283bed848a01b38fcaf64f395c1d2f72d"/>
            </game>
            """
        )

        game shouldBe Game(
            name = "Metal Slug Anthology (USA)",
            roms = setOf(
                Rom(
                    name = "Metal Slug Anthology (USA).iso",
                    size = 4699979776,
                    sha1 = "9c793e1283bed848a01b38fcaf64f395c1d2f72d",
                )
            )
        )
    }

    @Test
    fun `parse rom`() {
        val rom = xmlMapper.readValue<Rom>(
            """
            <rom name="Metal Slug Anthology (USA).iso" size="4699979776" crc="68ed804e" md5="16d7bab53fa02d4ddd61b47da66cef93" sha1="9c793e1283bed848a01b38fcaf64f395c1d2f72d"/>
            """
        )

        rom shouldBe Rom(
            name = "Metal Slug Anthology (USA).iso",
            size = 4699979776,
            sha1 = "9c793e1283bed848a01b38fcaf64f395c1d2f72d",
        )
    }
}
