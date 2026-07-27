package io.github.remmerw.buri

internal class BEScanner(private val reader: BEReader) {
    private var cachedPos: Int = 0
    private var cachedRemaining: Int = reader.size

    fun read(): Int {
        if (cachedRemaining > 0) {
            val byte = reader.read().toInt() and 0xFF
            cachedRemaining--
            return byte
        }
        return -1
    }

    fun peek(): Int {
        if (cachedRemaining > 0) {
            return reader.peek().toInt() and 0xFF
        }
        return -1
    }

    private fun readObject(builder: BEObjectBuilder): BEObject {
        while (cachedRemaining > 0) {
            val c = reader.peek().toInt() and 0xFF
            if (!builder.accept(c)) break
            reader.read()
            cachedRemaining--
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
