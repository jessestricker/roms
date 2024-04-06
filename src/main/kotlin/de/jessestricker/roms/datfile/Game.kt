package de.jessestricker.roms.datfile

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class Game(
    @field:JacksonXmlProperty(isAttribute = true)
    val name: String,

    @field:JacksonXmlProperty(localName = "rom")
    val roms: Set<Rom>,
)
