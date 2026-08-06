package io.github.remmerw.buri

import kotlinx.io.Sink
import kotlin.jvm.JvmInline

@JvmInline
value class BEInteger(private val value: Long) : BEObject {

    override fun encodeTo(sink: Sink) {
        val writer = BEWriter(sink)
        writer.longValue(value)
    }

    fun toLong(): Long {
        return value
    }

    fun toInt(): Int {
        return value.toInt()
    }
}
