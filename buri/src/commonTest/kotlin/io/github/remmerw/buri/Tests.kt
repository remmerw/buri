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
        val torrentString = "d8:announce37:http://example.org lengthi32768e6:pieces20:abcdefghijklmnopqrstee
"

        val torrentBytes = torrentString.toByteArray()

        val buffer = ByteBuffer.allocate(200)
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
