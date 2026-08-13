package io.github.remmerw.buri

import kotlin.jvm.JvmInline
import kotlinx.io.Sink

@JvmInline
value class BEList(
    private val list: List<BEObject>,
) : BEObject {
    override fun encodeTo(buffer: Buffer) {
        buffer.bencodeList()

        list.forEach { value ->
            value.encodeTo(buffer)
        }

        buffer.bencodeEof()
    }
override fun encodeTo(buffer: Sink) {
        buffer.bencodeList()

        list.forEach { value ->
            value.encodeTo(buffer)
        }

        buffer.bencodeEof()
    }
    fun toList(): List<BEObject> = list
}
