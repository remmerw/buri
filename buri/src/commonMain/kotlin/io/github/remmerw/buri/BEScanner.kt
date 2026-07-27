package io.github.remmerw.buri

internal class BEScanner(private val reader: BEReader) {

    fun read(): Int {
        if (!reader.exhausted()) {
            return reader.read().toInt() and 0xFF
        }
        return -1
    }

    fun peek(): Int {
        if (!reader.exhausted()) {
            return reader.peek().toInt() and 0xFF
        }
        return -1
    }

    fun readMapObject(builder: BEMapBuilder): BEMap {
        while (true) {
            val c = peek()
            if (c == -1) break
            if (!builder.accept(c)) break
            read()
        }
        return builder.build() as BEMap
    }

    fun readListObject(builder: BEListBuilder): BEList {
        while (true) {
            val c = peek()
            if (c == -1) break
            if (!builder.accept(c)) break
            read()
        }
        return builder.build() as BEList
    }

    fun readStringObject(builder: BEStringBuilder): BEString {
        while (true) {
            val c = peek()
            if (c == -1) break
            if (!builder.accept(c)) break
            read()
        }
        return builder.build()
    }

    fun readIntegerObject(builder: BEIntegerBuilder): BEInteger {
        while (true) {
            val c = peek()
            if (c == -1) break
            if (!builder.accept(c)) break
            read()
        }
        return builder.build() as BEInteger
    }
}
