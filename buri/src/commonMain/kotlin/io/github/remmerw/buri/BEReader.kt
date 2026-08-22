package io.github.remmerw.buri

import java.nio.ByteBuffer

class BEReader(
    val data: ByteBuffer
) {
    fun exhausted(): Boolean = data.hasRemaining()

    fun remaining(): Int = data.remaining()

    fun read(): Byte = data.readByte()

    fun peek(): Byte {
        val pos = data.position()
        val byte = read()
        data.position(pos)
        return byte
    }

    internal fun scannerRead(): Int {
        if (remaining() > 0) {
            return read().toInt() and 0xFF
        }
        return -1
    }

    internal fun scannerPeek(): Int {
        if (remaining() > 0) {
            return peek().toInt() and 0xFF
        }
        return -1
    }

    private fun readObject(builder: BEObjectBuilder): BEObject {
        while (remaining() > 0) {
            val c = peek().toInt() and 0xFF
            if (!builder.accept(c)) break
            read()
        }
        return builder.build()
    }

    internal fun readMapObject(builder: BEMapBuilder): BEMap = readObject(builder) as BEMap

    internal fun readListObject(builder: BEListBuilder): BEList = readObject(builder) as BEList

    internal fun readStringObject(builder: BEStringBuilder): BEString = readObject(builder) as BEString

    internal fun readIntegerObject(builder: BEIntegerBuilder): BEInteger = readObject(builder) as BEInteger
}
