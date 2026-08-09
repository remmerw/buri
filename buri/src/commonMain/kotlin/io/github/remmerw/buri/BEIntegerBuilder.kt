package io.github.remmerw.buri

internal class BEIntegerBuilder : BEPrefixedTypeBuilder() {
    private val stringBuilder = StringBuilder()

    override fun doAccept(b: Int): Boolean {
        val c = b.toChar()
        if (c.isDigit() || stringBuilder.isEmpty() && c == '-') {
            stringBuilder.append(c)
            return true
        }
        throw IllegalArgumentException("Unexpected token while reading integer (as ASCII char): $c") // Optimized: string interpolation
    }

    override fun acceptEOF(): Boolean = true

    override fun type(): BEType = BEType.INTEGER

    override fun doBuild(): BEInteger = BEInteger(stringBuilder.toString().toLong())
}
