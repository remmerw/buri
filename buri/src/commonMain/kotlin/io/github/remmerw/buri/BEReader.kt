package io.github.remmerw.buri

class BEReader(val data: ByteArray, val size: Int) {
    private var pos: Int = 0

    
    fun exhausted(): Boolean {
        return remaining() <= 0
    }

    fun remaining(): Int {
        return size - pos
    }

    fun peek(): Byte {
        return data[pos]
    }

    fun read(): Byte {
        val byte = data[pos]
        pos++
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

    internal fun readMapObject(builder: BEMapBuilder): BEMap {
        return readObject(builder) as BEMap
    }

    internal fun readListObject(builder: BEListBuilder): BEList {
        return readObject(builder) as BEList
    }

    internal fun readStringObject(builder: BEStringBuilder): BEString {
        return readObject(builder) as BEString
    }

    internal fun readIntegerObject(builder: BEIntegerBuilder): BEInteger {
        return readObject(builder) as BEInteger
    }
}
