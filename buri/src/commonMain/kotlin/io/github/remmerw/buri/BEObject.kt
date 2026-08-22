package io.github.remmerw.buri

import kotlinx.io.Sink
import java.nio.ByteBuffer

interface BEObject {
    fun encodeTo(buffer: ByteBuffer)

    fun encodeTo(sink: Sink)
}
