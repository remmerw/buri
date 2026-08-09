package io.github.remmerw.buri

internal class BEStringBuilder : BEObjectBuilder {
    private val numericLength = StringBuilder()
    private var result: ByteArray? = null
    private var length = 0
    private var bytesAcceptedCount = 0

    override fun accept(b: Int): Boolean {
        val c = b.toChar()
        if (result != null) {
            if (bytesAcceptedCount >= length) {
                return false
            }
            result!![bytesAcceptedCount] = b.toByte()
            bytesAcceptedCount++
            return true
        } else {
            if (c == DELIMITER) {
                bytesAcceptedCount = 0
                length = numericLength.toString().toInt()
                require(length in 0..MAX_SIZE) {
                    "Invalid length"
                }
                result = ByteArray(length)
                return true
            }
            if (!c.isDigit()) {
                throw IllegalArgumentException("Unexpected token while reading string's length (as ASCII char): $c")
            }
            numericLength.append(c)
            bytesAcceptedCount++
            return true
        }
    }

    override fun build(): BEString {
        requireNotNull(result) { "Can't build string: no content" }
        check(bytesAcceptedCount >= length) { "Can't build string: insufficient content" }
        return BEString(result!!)
    }

    override fun type(): BEType = BEType.STRING
}
