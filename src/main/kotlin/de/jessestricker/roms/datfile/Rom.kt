package de.jessestricker.roms.datfile

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class Rom(
    @field:JacksonXmlProperty(isAttribute = true)
    val name: String,

    @field:JacksonXmlProperty(isAttribute = true)
    val size: Long,

    @field:JacksonXmlProperty(isAttribute = true)
    val sha1: Sha1Digest,
)
