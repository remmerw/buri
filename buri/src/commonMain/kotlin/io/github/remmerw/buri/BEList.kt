package io.github.remmerw.buri

import kotlinx.io.Sink
import java.nio.ByteBuffer
import kotlin.jvm.JvmInline

@JvmInline
value class BEList(
    private val list: List<BEObject>,
) : BEObject {
    override fun encodeTo(buffer: ByteBuffer) {
        buffer.bencodeList()

        list.forEach { value ->
            value.encodeTo(buffer)
        }

        buffer.bencodeEof()
    }

    override fun encodeTo(sink: Sink) {
        sink.bencodeList()

        list.forEach { value ->
            value.encodeTo(sink)
        }

        sink.bencodeEof()
    }

    fun toList(): List<BEObject> = list
}
