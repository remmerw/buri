package io.github.remmerw.buri


internal class BEStringBuilder : BEObjectBuilder {
    private val numericLength = StringBuilder()  // Optimized: use StringBuilder instead of String
    private var result: ByteArray = byteArrayOf()
    private var length = 0
    private var bytesAcceptedCount = 0
    private var shouldReadBody = false


    override fun accept(b: Int): Boolean {
        val c = b.toChar()
        if (shouldReadBody) {
            if (bytesAcceptedCount >= length) {  // Optimized: simplified bounds check
                return false
            }
            result[bytesAcceptedCount] = b.toByte()
            bytesAcceptedCount++
            return true
        } else {

            if (c == DELIMITER) {
                shouldReadBody = true
                bytesAcceptedCount = 0
                length = numericLength.toString().toInt()
                result = ByteArray(length)
                return true
            }
            if (!c.isDigit()) {  // Optimized: explicit check instead of require() in hot path
                throw IllegalArgumentException("Unexpected token while reading string's length (as ASCII char): $c")
            }
            numericLength.append(c)  // Optimized: use StringBuilder.append() instead of String concatenation
            bytesAcceptedCount++
            return true
        }
    }

    override fun build(): BEString {
        check(shouldReadBody) { "Can't build string: no content" }
        check(bytesAcceptedCount >= length) { "Can't build string: insufficient content" }
        return BEString(result)
    }

    override fun type(): BEType {
        return BEType.STRING
    }

}
