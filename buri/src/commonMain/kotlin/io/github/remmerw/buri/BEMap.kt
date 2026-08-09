package io.github.remmerw.buri

import kotlinx.io.Sink
import kotlin.jvm.JvmInline

@JvmInline
value class BEMap(
    private val map: Map<String, BEObject>,
) : BEObject {
    override fun encodeTo(sink: Sink) {
        sink.bencodeMap()

        map.entries
            .sortedBy { it.key }
            .forEach { (key, value) ->
                sink.bencodeMapKey(key)
                value.encodeTo(sink)
            }
        sink.bencodeEof()
    }

    fun toMap(): Map<String, BEObject> = map
}
