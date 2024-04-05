package de.jessestricker.roms

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

internal val xmlMapper = XmlMapper().apply {
    registerKotlinModule { enable(KotlinFeature.StrictNullChecks) }
    enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
}

data class Datfile(
    val header: Header,

    @JacksonXmlProperty(localName = "game")
    @JacksonXmlElementWrapper(useWrapping = false)
    val games: Set<Game>,
) {
    companion object {
        fun load(file: File): Datfile {
            return xmlMapper.readValue(file)
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Header(
    val name: String,
    val version: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Game(
    @JacksonXmlProperty(isAttribute = true)
    val name: String,

    @JacksonXmlProperty(localName = "rom")
    @JacksonXmlElementWrapper(useWrapping = false)
    val roms: Set<Rom>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Rom(
    @JacksonXmlProperty(isAttribute = true)
    val name: String,

    @JacksonXmlProperty(isAttribute = true)
    val size: Long,

    @JacksonXmlProperty(isAttribute = true)
    val sha1: String,
)
