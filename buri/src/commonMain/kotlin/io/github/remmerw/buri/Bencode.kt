package io.github.remmerw.buri

internal const val MAX_SIZE: Int = 2 * 1024 * 1024 // 2 MB

fun decodeBencodeToString(buffer: Buffer): String = (buffer.decodeBencode() as BEString).toString()

fun decodeBencodeToLong(buffer: Buffer): Long = (buffer.decodeBencode() as BEInteger).toLong()

fun decodeBencodeToMap(buffer: Buffer): Map<String, BEObject> = (buffer.decodeBencode() as BEMap).toMap()

fun decodeBencodeToList(buffer: Buffer): List<BEObject> = (buffer.decodeBencode() as BEList).toList()

fun BEReader.decodeBencode(): BEObject {
    val parser = createParser(this)
    return when (parser.readType()) {
        BEType.STRING -> parser.readString()
        BEType.INTEGER -> parser.readInteger()
        BEType.LIST -> parser.readList()
        BEType.MAP -> parser.readMap()
    }
}

fun Buffer.decodeBencode(): BEObject = BEReader(this.data, this.length).decodeBencode()

fun Byte.bencode(): BEInteger = BEInteger(toLong())

fun Int.bencode(): BEInteger = BEInteger(toLong())

fun Long.bencode(): BEInteger = BEInteger(this)

fun String.bencode(): BEString = BEString(encodeToByteArray())

fun ByteArray.bencode(): BEString = BEString(this)

fun List<BEObject>.bencode(): BEList = BEList(this)

fun Map<String, BEObject>.bencode(): BEMap = BEMap(this)
