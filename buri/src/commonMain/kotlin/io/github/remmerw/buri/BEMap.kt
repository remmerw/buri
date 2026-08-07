package io.github.remmerw.buri

import kotlinx.io.Sink
import kotlin.jvm.JvmInline

@JvmInline
value class BEMap(private val map: Map<String, BEObject>) :
    BEObject {

    override fun encodeTo(sink: Sink) {
        sink.bencodeMap()

        
        val sortedEntries = map.entries
            .sortedBy { it.key }
            .map { (key, value) ->
                Pair(key, value) 
            }

        for ((key, value) in sortedEntries) {
            // Write key length and delimiter
            sink.bencodeMapKey(key)
            // Write value
            value.encodeTo(sink)
        }
        sink.bencodeEof()
    }

    fun toMap(): Map<String, BEObject> {
        return map
    }

}
