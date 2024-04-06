package de.jessestricker.roms.datfile

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

data class Datfile(
    val header: Header,

    @field:JacksonXmlProperty(localName = "game")
    val games: Set<Game>,
) {
    companion object {
        private val xmlMapper = XmlMapper.builder()
            .addModule(kotlinModule { enable(KotlinFeature.StrictNullChecks) })
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .defaultUseWrapper(false)
            .build()

        fun load(file: File): Datfile {
            return xmlMapper.readValue(file)
        }
    }
}
