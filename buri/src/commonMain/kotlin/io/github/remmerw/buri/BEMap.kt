package io.github.remmerw.buri

import kotlinx.io.Sink
import kotlin.jvm.JvmInline

@JvmInline
value class BEMap(private val map: Map<String, BEObject>) :
    BEObject {

    override fun encodeTo(sink: Sink) {
        sink.writeByte(MAP_PREFIX.code.toByte())

        // Optimized: pre-compute byte arrays to avoid double encoding
        val sortedEntries = map.entries
            .sortedBy { it.key }
            .map { (key, value) ->
                Pair(key.encodeToByteArray(), value)  // Cache the encoded key bytes
            }

        for ((keyBytes, value) in sortedEntries) {
            // Write key length and delimiter
            sink.write(keyBytes.size.toString().encodeToByteArray())
            sink.writeByte(DELIMITER.code.toByte())
            // Write cached key bytes
            sink.write(keyBytes)
            // Write value
            value.encodeTo(sink)
        }
        sink.writeByte(EOF.code.toByte())
    }

    fun toMap(): Map<String, BEObject> {
        return map
    }

}
