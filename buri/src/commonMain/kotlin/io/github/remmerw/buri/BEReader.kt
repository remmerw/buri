package io.github.remmerw.buri

class BEReader(val data: ByteArray, val size: Int) {
    private var pos: Int = 0
    private var cachedRemaining: Int = size

    // Original BEReader methods
    fun exhausted(): Boolean {
        return cachedRemaining <= 0
    }

    fun remaining(): Int {
        return cachedRemaining
    }

    fun peek(): Byte {
        return data[pos]
    }

    fun read(): Byte {
        val byte = data[pos]
        pos++
        cachedRemaining--
        return byte
    }

    // Integrated BEScanner methods
    fun scannerRead(): Int {
        if (cachedRemaining > 0) {
            return read().toInt() and 0xFF
        }
        return -1
    }

    fun scannerPeek(): Int {
        if (cachedRemaining > 0) {
            return peek().toInt() and 0xFF
        }
        return -1
    }

    private fun readObject(builder: BEObjectBuilder): BEObject {
        while (cachedRemaining > 0) {
            val c = peek().toInt() and 0xFF
            if (!builder.accept(c)) break
            read()
        }
        return builder.build()
    }

    fun readMapObject(builder: BEMapBuilder): BEMap {
        return readObject(builder) as BEMap
    }

    fun readListObject(builder: BEListBuilder): BEList {
        return readObject(builder) as BEList
    }

    fun readStringObject(builder: BEStringBuilder): BEString {
        return readObject(builder) as BEString
    }

    fun readIntegerObject(builder: BEIntegerBuilder): BEInteger {
        return readObject(builder) as BEInteger
    }
}
