package io.github.remmerw.buri

import kotlin.jvm.JvmInline

import kotlinx.io.Sink

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
