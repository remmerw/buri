package io.github.remmerw.buri

import kotlinx.io.Sink
import kotlin.jvm.JvmInline

@JvmInline
value class BEMap(private val map: Map<String, BEObject>) :
    BEObject {

    override fun encodeTo(sink: Sink) {
        sink.bencodeMap()

        // Optimized: pre-compute byte arrays to avoid double encoding
        val sortedEntries = map.entries
            .sortedBy { it.key }
            .map { (key, value) ->
                Pair(key, value)  // Cache the encoded key bytes
            }

        for ((key, value) in sortedEntries) {
            // Write key length and delimiter
            sink.bencodeMapEntry(key)
            // Write value
            value.encodeTo(sink)
        }
        sink.bencodeEof()
    }

    fun toMap(): Map<String, BEObject> {
        return map
    }

}
