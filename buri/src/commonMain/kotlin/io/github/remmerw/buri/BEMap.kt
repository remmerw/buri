package io.github.remmerw.buri


import java.nio.ByteBuffer
import kotlin.jvm.JvmInline

@JvmInline
value class BEMap(
    private val map: Map<String, BEObject>,
) : BEObject {
    override fun encodeTo(buffer: ByteBuffer) {
        buffer.bencodeMap()

        map.entries
            .sortedBy { it.key }
            .forEach { (key, value) ->
                buffer.bencodeMapKey(key)
                value.encodeTo(buffer)
            }
        buffer.bencodeEof()
    }

    fun toMap(): Map<String, BEObject> = map
}
