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
        var c: Int

        while ((peek().also { c = it }) != -1) {
            if (builder.accept(c)) {
                read()
            } else {
                break
            }
        }
        return builder.build() as BEMap
    }

    fun readListObject(builder: BEListBuilder): BEList {
        var c: Int

        while ((peek().also { c = it }) != -1) {
            if (builder.accept(c)) {
                read()
            } else {
                break
            }
        }
        return builder.build() as BEList
    }

    fun readStringObject(builder: BEStringBuilder): BEString {
        var c: Int

        while ((peek().also { c = it }) != -1) {
            if (builder.accept(c)) {
                read()
            } else {
                break
            }
        }
        return builder.build()
    }

    fun readIntegerObject(builder: BEIntegerBuilder): BEInteger {
        var c: Int

        while ((peek().also { c = it }) != -1) {
            if (builder.accept(c)) {
                read()
            } else {
                break
            }
        }
        return builder.build() as BEInteger
    }
}