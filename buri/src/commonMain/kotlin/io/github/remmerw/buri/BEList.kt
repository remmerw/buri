package io.github.remmerw.buri

import kotlinx.io.Sink
import kotlin.jvm.JvmInline

@JvmInline
value class BEList(private val list: List<BEObject>) : BEObject {

    override fun encodeTo(sink: Sink) {
        val writer = BEWriter(sink)
        writer.list()

        list.forEach { value ->
            value.encodeTo(sink)
        }

        writer.eof()
    }

    fun toList(): List<BEObject> {
        return list
    }
}
