package io.github.remmerw.buri

import kotlinx.io.Sink

interface BEObject {
    fun encodeTo(buffer: Buffer)
    fun encodeTo(sink: Sink)
}
