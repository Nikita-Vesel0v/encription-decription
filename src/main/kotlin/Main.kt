package encryptdecrypt

fun main() {
    val codeDict = buildMap { for (ch in 'a'..'z') this[ch] = 'z' - (ch - 'a') }

    val toEncrypt = "we found a treasure!"

    val codedInput = buildString {
        toEncrypt.lowercase().forEach {
            append(if (it in codeDict) codeDict[it] else it)
        }
    }
    println(codedInput)
}