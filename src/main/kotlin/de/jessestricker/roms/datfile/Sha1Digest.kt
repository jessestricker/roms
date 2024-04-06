package de.jessestricker.roms.datfile

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.util.StdConverter
import java.util.*

@JsonDeserialize(converter = StringToSha1DigestConverter::class)
data class Sha1Digest(
    private val value: ByteArray,
) {
    override fun toString(): String {
        return HEX_FORMAT.formatHex(value)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Sha1Digest
        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }
}

fun String.toSha1Digest(): Sha1Digest {
    return Sha1Digest(HEX_FORMAT.parseHex(this))
}

private val HEX_FORMAT = HexFormat.of()

private class StringToSha1DigestConverter : StdConverter<String, Sha1Digest>() {
    override fun convert(value: String): Sha1Digest {
        return value.toSha1Digest()
    }
}
