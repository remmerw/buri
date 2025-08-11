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

}


