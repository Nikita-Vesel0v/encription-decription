package encryptdecrypt

const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

fun main() {
    val toEncrypt = readln()
    val key = readln().toInt()
    val codeDict = buildMap {
        for (ch in 0..25) {
            this[ALPHABET[ch]] = ALPHABET[(ch + key) % 26]
        }
    }
    val codedInput = buildString {
        toEncrypt.lowercase().forEach {
            append(if (it in codeDict) codeDict[it] else it)
        }
    }
    println(codedInput)
}