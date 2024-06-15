package encryptdecrypt

import java.io.File

const val ALPHABET_LOW = "abcdefghijklmnopqrstuvwxyz"
val ALPHABET_UP = ALPHABET_LOW.uppercase()

class Cryptographer(args: Array<String>) {
    private var mode: String = "enc"
    private var data: String = ""
    private var key: Int = 0
    private var output = ""
    private var outputFilename = ""
    private var alg = "shift"
    private var codeDict = hashMapOf<Char, Char>()

    init {
        for (i in 0 until args.size / 2) {
            try {
                when (args[i * 2]) {
                    "-mode" -> mode = args[i * 2 + 1].lowercase()
                    "-data" -> data = args[i * 2 + 1]
                    "-key" -> key = args[i * 2 + 1].toInt()
                    "-in" -> data = File(args[i * 2 + 1]).readText()
                    "-out" -> outputFilename = args[i * 2 + 1]
                    "-alg" -> alg = args[i * 2 + 1]
                }
            } catch (e: Exception) {
                throw IllegalArgumentException("Error - Invalid input data: ${args.joinToString(" ")}")
            }
        }
    }
    fun menu() {
        output = when (alg) {
            "unicode" -> algUnicode()
            "shift" -> algShift()
            else -> throw Exception("Error - Unknown algorithm: $alg")
        }
        if (outputFilename.isNotEmpty()) { File(outputFilename).writeText(output) }
        else println(output)
    }
    private fun algUnicode() =
        when (mode) {
            "enc" -> data.map { it + key }.joinToString("")
            "dec" -> data.map { it - key }.joinToString("")
            else -> throw Exception("Error - Unknown mode: $mode")
        }
    private fun algShift() = buildString {
        codeDict.apply {
            for (ch in 0..25) {
                when (mode) {
                    "enc" -> {
                        this[ALPHABET_LOW[ch]] = ALPHABET_LOW[(ch + key) % 26]
                        this[ALPHABET_UP[ch]] = ALPHABET_UP[(ch + key) % 26]
                    }
                    "dec" -> {
                        this[ALPHABET_LOW[(ch + key) % 26]] = ALPHABET_LOW[ch]
                        this[ALPHABET_UP[(ch + key) % 26]] = ALPHABET_UP[ch]
                    }
                }
            }
        }
        data.forEach { append(if (it in codeDict) codeDict[it] else it) }
    }
}

fun createArgs(): Array<String> {
    var args = arrayOf<String>()
    print("Mode: "); args += "-mode"; args += readln()
    print("Data: "); args += "-data"; args += readln()
    print("Key: "); args += "-key"; args += readln()
    return args
}

fun main(args: Array<String>) {
    val programArgs = if (args.isEmpty()) createArgs() else args
    val cryptographer = Cryptographer(programArgs)
    cryptographer.menu()
}