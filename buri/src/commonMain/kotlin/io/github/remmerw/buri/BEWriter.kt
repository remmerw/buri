package io.github.remmerw.buri

import kotlinx.io.Sink

fun Sink.bencode(value: ByteArray) {
    this.write(value.size.toString().encodeToByteArray())
    this.writeByte(DELIMITER.code.toByte())
    this.write(value)
}

fun Sink.bencode(value: BEObject) {
    value.encodeTo(this)
}

fun Sink.bencode(value: String) {
    this.bencode(value.encodeToByteArray())
}

fun Sink.bencode(value: Long) {
    this.writeByte(INTEGER_PREFIX.code.toByte())
    this.write(value.toString().encodeToByteArray())
    this.writeByte(EOF.code.toByte())
}

fun Sink.bencode(value: Int) {
    this.bencode(value.toLong())
}

fun Sink.bencodeEof() {
    this.writeByte(EOF.code.toByte())
}

fun Sink.bencodeMap() {
    this.writeByte(MAP_PREFIX.code.toByte())
}

fun Sink.bencodeList() {
    this.writeByte(LIST_PREFIX.code.toByte())
}

fun Sink.bencodeMapKey(key: String) {
    val keyBytes = key.encodeToByteArray()

    // Write key length and delimiter
    this.write(keyBytes.size.toString().encodeToByteArray())

    this.writeByte(DELIMITER.code.toByte())
    this.write(keyBytes)
}

fun Buffer.bencode(value: ByteArray) {
    this.write(value.size.toString().encodeToByteArray())
    this.writeByte(DELIMITER.code.toByte())
    this.write(value)
}

fun Buffer.bencode(value: BEObject) {
    value.encodeTo(this)
}

fun Buffer.bencode(value: String) {
    this.bencode(value.encodeToByteArray())
}

fun Buffer.bencode(value: Long) {
    this.writeByte(INTEGER_PREFIX.code.toByte())
    this.write(value.toString().encodeToByteArray())
    this.writeByte(EOF.code.toByte())
}

fun Buffer.bencode(value: Int) {
    this.bencode(value.toLong())
}

fun Buffer.bencodeEof() {
    this.writeByte(EOF.code.toByte())
}

fun Buffer.bencodeMap() {
    this.writeByte(MAP_PREFIX.code.toByte())
}

fun Buffer.bencodeList() {
    this.writeByte(LIST_PREFIX.code.toByte())
}

fun Buffer.bencodeMapKey(key: String) {
    val keyBytes = key.encodeToByteArray()

    // Write key length and delimiter
    this.write(keyBytes.size.toString().encodeToByteArray())

    this.writeByte(DELIMITER.code.toByte())
    this.write(keyBytes)
}
