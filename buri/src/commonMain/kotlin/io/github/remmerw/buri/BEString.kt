package io.github.remmerw.buri

import kotlinx.io.Sink
import kotlin.jvm.JvmInline

@JvmInline
value class BEString(
    private val content: ByteArray,
) : BEObject {
    override fun encodeTo(sink: Sink) {
        sink.bencode(content)
    }

    override fun toString(): String = content.decodeToString()

    fun toByteArray(): ByteArray = content
}
