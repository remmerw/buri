package io.github.remmerw.buri


import java.nio.ByteBuffer
import kotlin.jvm.JvmInline

@JvmInline
value class BEString(
    private val content: ByteArray,
) : BEObject {
    override fun encodeTo(buffer: ByteBuffer) {
        buffer.bencode(content)
    }

    override fun toString(): String = content.decodeToString()

    fun toByteArray(): ByteArray = content
}
