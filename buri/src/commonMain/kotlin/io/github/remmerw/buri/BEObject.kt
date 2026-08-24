package io.github.remmerw.buri

import java.nio.ByteBuffer

interface BEObject {
    fun encodeTo(buffer: ByteBuffer)
}
