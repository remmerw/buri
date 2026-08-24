package io.github.remmerw.buri

import java.nio.ByteBuffer
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class Tests {
    @Test
    fun decodeMap() {
        val torrentString = "d8:announce42:http://example.com lengthi16384e6:pieces20:12345678901234567890ee"

        val torrentBytes = torrentString.toByteArray()

val torrentBytes = byteArrayOf(
    100, 56, 58, 97, 110, 110, 111, 117, 110, 99, 101, 52, 50, 58, 104, 116, 
    116, 112, 58, 47, 47, 116, 114, 97, 99, 107, 101, 114, 46, 101, 120, 97, 
    109, 112, 108, 101, 46, 99, 111, 109, 58, 56, 48, 56, 48, 47, 97, 110, 
    110, 111, 117, 110, 99, 101, 52, 58, 105, 110, 102, 111, 100, 54, 58, 108, 
    101, 110, 103, 116, 104, 105, 49, 48, 50, 52, 101, 52, 58, 110, 97, 109, 
    101, 56, 58, 116, 101, 115, 116, 46, 116, 120, 116, 49, 50, 58, 112, 105, 
    101, 99, 101, 32, 108, 101, 110, 103, 116, 104, 105, 49, 54, 51, 56, 52, 
    101, 54, 58, 112, 105, 101, 99, 101, 115, 50, 48, 58, 49, 50, 51, 52, 53, 
    54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 101, 101
)

        val buffer = ByteBuffer.allocate(100)
        buffer.put(torrentBytes)
        buffer.rewind()
        val map = decodeBencodeToMap(buffer)
        assertNotNull(map)
    }

    @Test
    fun encodeDecodeStringWithDara() {
        val testData = "hi"
        val buffer = ByteBuffer.allocate(100)
        testData.bencode().encodeTo(buffer)
        val array = byteArrayOf(10, 20, 30, 40)
        buffer.put(array)
        buffer.flip()
        val cmp = decodeBencodeToString(buffer)
        assertEquals(cmp, testData)
        val cmpArray = ByteArray(4)
        buffer.get(cmpArray)
        assertTrue(cmpArray.contentEquals(array))
    }

    @Test
    fun encodeDecodeString() {
        val testData = "hi"
        val buffer = ByteBuffer.allocate(100)
        testData.bencode().encodeTo(buffer)
        buffer.flip()
        val cmp = decodeBencodeToString(buffer)
        assertEquals(cmp, testData)
    }

    @Test
    fun encodeDecodeInteger() {
        val value = 6666L
        val buffer = ByteBuffer.allocate(100)
        value.bencode().encodeTo(buffer)
        buffer.flip()
        val cmp = decodeBencodeToLong(buffer)
        assertEquals(cmp, value)
    }

    @Test
    fun encodeDecodeEmptyList() {
        val value: List<BEObject> = emptyList()
        val buffer = ByteBuffer.allocate(100)
        value.bencode().encodeTo(buffer)
        buffer.flip()
        val cmp = decodeBencodeToList(buffer)
        assertEquals(cmp, value)
    }

    @Test
    fun encodeDecodeEmptyMap() {
        val value: Map<String, BEObject> = emptyMap()
        val buffer = ByteBuffer.allocate(100)
        value.bencode().encodeTo(buffer)
        buffer.flip()
        val cmp = decodeBencodeToMap(buffer)
        assertEquals(cmp, value)
    }

    @Test
    fun encodeDecodeEmptyMapWithData() {
        val value: Map<String, BEObject> = emptyMap()
        val buffer = ByteBuffer.allocate(100)
        value.bencode().encodeTo(buffer)
        val array = byteArrayOf(10, 20, 30, 40)
        buffer.put(array)
        buffer.flip()
        val cmp = decodeBencodeToMap(buffer)
        assertEquals(cmp, value)
        val cmpArray = ByteArray(4)
        buffer.get(cmpArray)
        assertTrue(cmpArray.contentEquals(array))
    }

    @Test
    fun encodeDecodeList() {
        val value: List<BEObject> =
            listOf(
                555L.bencode(),
                "hello".bencode(),
            )
        val buffer = ByteBuffer.allocate(200)
        value.bencode().encodeTo(buffer)
        buffer.flip()

        val list = decodeBencodeToList(buffer)
        assertEquals(value.size, list.size)
        val a = value.first() as BEInteger
        assertEquals(a.toInt(), 555)
        val b = value.last() as BEString
        assertContentEquals(b.toByteArray(), "hello".encodeToByteArray())
    }

    @Test
    fun coverage() {
        assertNotNull('1'.code.toByte().bencode())
        assertNotNull(1.bencode())
        assertNotNull(1L.bencode())
        assertNotNull("".bencode())
        assertNotNull(byteArrayOf().bencode())
    }

    @Test
    fun examplesList() {
        // prepare data
        val value: List<BEObject> =
            listOf(
                555L.bencode(),
                "hello".bencode(),
            )
        val buffer = ByteBuffer.allocate(200)

        // encode
        value.bencode().encodeTo(buffer)
        buffer.flip()
        // decode
        val list = (buffer.decodeBencode() as BEList).toList()

        // testing
        assertEquals(value.size, list.size)
        val a = value.first() as BEInteger
        assertEquals(a.toInt(), 555)
        val b = value.last() as BEString
        assertContentEquals(b.toByteArray(), "hello".encodeToByteArray())
    }
}
