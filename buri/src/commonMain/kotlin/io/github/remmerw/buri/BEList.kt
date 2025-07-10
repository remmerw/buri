package io.github.remmerw.buri

import kotlinx.io.Sink
import kotlin.jvm.JvmInline

@JvmInline
value class BEList(private val list: List<BEObject>) : BEObject {

    override fun encodeTo(sink: Sink) {
        sink.writeByte(LIST_PREFIX.code.toByte())

        list.forEach { value ->
            value.encodeTo(sink)
        }

        sink.writeByte(EOF.code.toByte())
    }

    fun toList(): List<BEObject> {
        return list
    }
}
