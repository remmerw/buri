class Buffer(private val data: ByteArray) {
    
    constructor(size: Int) : this(ByteArray(size))

    private var index: Int = 0

    // Kotlin-Property statt Funktion length()
    val length: Int
        get() = index

    fun writeBuffer(byte: Byte) {
        if (index < data.size) {
            data[index] = byte
            index++
        } else {
            throw Exception()
        }
    }

    fun write(bytes: ByteArray) {
        if (bytes.size <= remaining) {
            bytes.copyInto(destination = data, destinationOffset = index)
            index += bytes.size
        } else {
            throw Exception()
        }
    }

    fun reset() {
        index = 0
    }

}
