package io.github.remmerw.buri

import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.readByteArray

fun decodeBencodeToString(source: Source): String = (source.decodeBencode() as BEString).toString()

fun decodeBencodeToLong(source: Source): Long = (source.decodeBencode() as BEInteger).toLong()

fun decodeBencodeToMap(source: Source): Map<String, BEObject> = (source.decodeBencode() as BEMap).toMap()

fun decodeBencodeToList(source: Source): List<BEObject> = (source.decodeBencode() as BEList).toList()

fun BEReader.decodeBencode(): BEObject {
    val parser = createParser(this)
    return when (parser.readType()) {
        BEType.STRING -> parser.readString()
        BEType.INTEGER -> parser.readInteger()
        BEType.LIST -> parser.readList()
        BEType.MAP -> parser.readMap()
    }
}

fun Source.decodeBencode(): BEObject {
    val data = this.readByteArray()
    return BEReader(data, data.size).decodeBencode()
}

fun Byte.bencode(): BEInteger = BEInteger(toLong())

fun Int.bencode(): BEInteger = BEInteger(toLong())

fun Long.bencode(): BEInteger = BEInteger(this)

fun String.bencode(): BEString = BEString(encodeToByteArray())

fun ByteArray.bencode(): BEString = BEString(this)

fun List<BEObject>.bencode(): BEList = BEList(this)

fun List<BEObject>.encodeBencodeTo(sink: Sink) {
    this.bencode().encodeTo(sink)
}

fun Map<String, BEObject>.bencode(): BEMap = BEMap(this)

fun Map<String, BEObject>.encodeBencodeTo(sink: Sink) {
    this.bencode().encodeTo(sink)
}
