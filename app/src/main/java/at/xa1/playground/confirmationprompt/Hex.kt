package at.xa1.playground.confirmationprompt

fun Byte.toHex() = "%02x".format(this)
fun ByteArray.toHex() = joinToString("") { byte -> byte.toHex() }

fun ByteArray.toAsciiOrHex(): String {
    val result = StringBuilder()

    forEach { byte ->
        when (val char = byte.toChar()) {
            in VISIBLE_ASCII_CHARS, ' ' -> result.append(char)
            else -> result.append("[x${byte.toHex()}]")
        }
    }

    return result.toString()
}

private val VISIBLE_ASCII_CHARS = 32.toChar()..126.toChar()