package io.github.remmerw.buri

internal interface BEObjectBuilder {
    fun accept(b: Int): Boolean

    fun build(): BEObject

    fun type(): BEType
}
