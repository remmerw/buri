class Buffer(private val data: ByteArray) {
    // Zweiter Konstruktor für eine bequemere Instanziierung über die Größe
    constructor(size: Int) : this(ByteArray(size))

    // Interner Schreib- und Lese-Index
    private var index: Int = 0

    // Kotlin-Property statt Funktion length()
    val length: Int
        get() = index

    fun write(byte: Byte) {
        if (index < data.size) {
            data[index] = byte
            index++
        } else {
            throw IndexOutOfBoundsException("Buffer ist voll. Index: $index, Kapazität: ${data.size}")
        }
    }

    fun write(bytes: ByteArray) {
        if (bytes.size <= remaining) {
            bytes.copyInto(destination = data, destinationOffset = index)
            index += bytes.size
        } else {
            throw IndexOutOfBoundsException("Nicht genug Platz im Buffer. Benötigt: ${bytes.size}, Verfügbar: $remaining")
        }
    }

    fun reset() {
        index = 0
    }

}
