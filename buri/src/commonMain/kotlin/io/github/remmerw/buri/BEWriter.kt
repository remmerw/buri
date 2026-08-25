package io.github.remmerw.buri

import java.nio.ByteBuffer

fun ByteBuffer.bencodeArray(size: Int) {
    size.toString().forEach { char ->
        this.put(char.code.toByte())
    }
    this.put(DELIMITER.code.toByte())
}

fun ByteBuffer.bencodeArrayData(value: ByteArray) {
    this.put(value)
}

fun ByteBuffer.bencodeArrayData(value: UShort) {
    this.putShort(value.toShort())
}

fun ByteBuffer.bencodeArrayData(value: Byte) {
    this.put(value)
}

fun ByteBuffer.bencode(value: ByteArray) {
    this.bencodeArray(value.size)
    this.bencodeArrayData(value)
}

fun ByteBuffer.bencode(value: String) {
    this.bencode(value.encodeToByteArray())
}

fun ByteBuffer.bencode(value: Long) {
    this.put(INTEGER_PREFIX.code.toByte())
    value.toString().forEach { char ->
        this.put(char.code.toByte())
    }
    this.put(EOF.code.toByte())
}

fun ByteBuffer.bencode(value: Int) {
    this.bencode(value.toLong())
}

fun ByteBuffer.bencodeEof() {
    this.put(EOF.code.toByte())
}

fun ByteBuffer.bencodeMap() {
    this.put(MAP_PREFIX.code.toByte())
}

fun ByteBuffer.bencodeList() {
    this.put(LIST_PREFIX.code.toByte())
}

fun ByteBuffer.bencodeMapKey(key: String) {
    val keyBytes = key.encodeToByteArray()

    keyBytes.size.toString().forEach { char ->
        this.put(char.code.toByte())
    }

    this.put(DELIMITER.code.toByte())
    this.put(keyBytes)
}
