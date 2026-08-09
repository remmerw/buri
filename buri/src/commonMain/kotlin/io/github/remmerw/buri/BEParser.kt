package io.github.remmerw.buri

internal class BEParser internal constructor(
    private val type: BEType,
    private val reader: BEReader,
) {
    fun readType(): BEType = type

    fun readMap(): BEMap = readMapObject(BEMapBuilder())

    fun readList(): BEList = readListObject(BEListBuilder())

    fun readString(): BEString = readStringObject(BEStringBuilder())

    fun readInteger(): BEInteger = readIntegerObject(BEIntegerBuilder())

    private fun readListObject(builder: BEListBuilder): BEList = reader.readListObject(builder)

    private fun readMapObject(builder: BEMapBuilder): BEMap = reader.readMapObject(builder)

    private fun readIntegerObject(builder: BEIntegerBuilder): BEInteger = reader.readIntegerObject(builder)

    private fun readStringObject(builder: BEStringBuilder): BEString = reader.readStringObject(builder)
}

const val DELIMITER: Char = ':'
const val EOF: Char = 'e'
const val INTEGER_PREFIX: Char = 'i'
const val LIST_PREFIX: Char = 'l'
const val MAP_PREFIX: Char = 'd'

internal fun createParser(reader: BEReader): BEParser {
    val type = getTypeForPrefix(reader.scannerPeek().toChar())
    return BEParser(type, reader)
}

internal fun getPrefixForType(type: BEType): Char =
    when (type) {
        BEType.INTEGER -> INTEGER_PREFIX
        BEType.LIST -> LIST_PREFIX
        BEType.MAP -> MAP_PREFIX
        else -> throw IllegalArgumentException("Unknown type: ${type.name.lowercase()}")
    }

internal fun getTypeForPrefix(c: Char): BEType {
    if (c.isDigit()) {
        return BEType.STRING
    }
    return when (c) {
        INTEGER_PREFIX -> {
            BEType.INTEGER
        }

        LIST_PREFIX -> {
            BEType.LIST
        }

        MAP_PREFIX -> {
            BEType.MAP
        }

        else -> throw IllegalStateException("Invalid type prefix: $c")
    }
}

internal fun builderForType(type: BEType): BEObjectBuilder =
    when (type) {
        BEType.STRING -> {
            BEStringBuilder()
        }

        BEType.INTEGER -> {
            BEIntegerBuilder()
        }

        BEType.LIST -> {
            BEListBuilder()
        }

        BEType.MAP -> {
            BEMapBuilder()
        }
    }
