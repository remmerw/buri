package io.github.remmerw.buri

import kotlinx.io.Sink
import kotlin.jvm.JvmInline

@JvmInline
value class BEList(
    private val list: List<BEObject>,
) : BEObject {
    override fun encodeTo(sink: Sink) {
        sink.bencodeList()

        list.forEach { value ->
            value.encodeTo(sink)
        }

        sink.bencodeEof()
    }
    override fun encodeTo(buffer: Buffer) {
        buffer.bencodeList()

        list.forEach { value ->
            value.encodeTo(buffer)
        }

        buffer.bencodeEof()
    }
    fun toList(): List<BEObject> = list
}
