package io.github.remmerw.buri

class BEReader(
    val data: ByteArray,
    val size: Int,
) {
    init {
        // Checks if sie is between 0 and MAX_SIZE (inclusive)
        require(size in 0..MAX_SIZE) { 
            "Invalid size" 
        }
    }
    private var pos: Int = 0

    fun exhausted(): Boolean = remaining() <= 0

    fun remaining(): Int = size - pos

    fun peek(): Byte = data[pos]

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

    internal fun readMapObject(builder: BEMapBuilder): BEMap = readObject(builder) as BEMap

    internal fun readListObject(builder: BEListBuilder): BEList = readObject(builder) as BEList

    internal fun readStringObject(builder: BEStringBuilder): BEString = readObject(builder) as BEString

    internal fun readIntegerObject(builder: BEIntegerBuilder): BEInteger = readObject(builder) as BEInteger
}
