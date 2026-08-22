package io.github.remmerw.buri

import kotlinx.io.Sink
import java.nio.ByteBuffer
import kotlin.jvm.JvmInline

@JvmInline
value class BEInteger(
    private val value: Long,
) : BEObject {
    override fun encodeTo(buffer: ByteBuffer) {
        buffer.bencode(value)
    }

    override fun encodeTo(sink: Sink) {
        sink.bencode(value)
    }

    fun toLong(): Long = value

    fun toInt(): Int = value.toInt()
}
