package io.github.remmerw.buri

import kotlin.jvm.JvmInline
import kotlinx.io.Sink


@JvmInline
value class BEString(
    private val content: ByteArray,
) : BEObject {
    override fun encodeTo(buffer: Buffer) {
        buffer.bencode(content)
    }
override fun encodeTo(buffer: Sink) {
        buffer.bencode(content)
    }
    override fun toString(): String = content.decodeToString()

    fun toByteArray(): ByteArray = content
}
