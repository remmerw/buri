package io.github.remmerw.buri

import kotlinx.io.Sink
import kotlin.jvm.JvmInline

@JvmInline
value class BEInteger(
    private val value: Long,
) : BEObject {
    override fun encodeTo(buffer: Buffer) {
        buffer.bencode(value)
    }

    override fun encodeTo(buffer: Sink) {
        buffer.bencode(value)
    }

    fun toLong(): Long = value

    fun toInt(): Int = value.toInt()
}
