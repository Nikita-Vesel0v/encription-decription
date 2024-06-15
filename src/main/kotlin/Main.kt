package encryptdecrypt

import java.io.File

const val ALPHABET_LOW = "abcdefghijklmnopqrstuvwxyz"
val ALPHABET_UP = ALPHABET_LOW.uppercase()

class Cryptographer(args: Array<String>) {
    private var config = hashMapOf(
        "mode" to "enc",
        "data" to "",
        "key" to "0",
        "out" to "", //filename to output
        "alg" to "shift",
    )
    private var output = ""
    private var codeDict = hashMapOf<Char, Char>()

    init {
        for (i in args.indices step 2) { //because (args.size / 2) - count of parameters
            try {
                when (args[i]) {
                    "-mode" -> config["mode"] = args[i + 1].lowercase()
                    "-alg" -> config["alg"] = args[i + 1]
                    "-data" -> config["data"] = args[i + 1]
                    "-key" -> config["key"] = args[i + 1]
                    "-in" -> config["data"] = File(args[i + 1]).readText()
                    "-out" -> config["out"] = args[i + 1]
                }
            } catch (e: Exception) {
                throw IllegalArgumentException("Error - Invalid input data: ${args.joinToString(" ")}")
            }
        }
    }
    fun menu() {
        output = when (config["alg"]) {
            "unicode" -> algUnicode()
            "shift" -> algShift()
            else -> throw Exception("Error - Unknown algorithm: ${config["alg"]}")
        }
        if (config["outputFilename"].isNullOrBlank()) config["out"]?.let { File(it).writeText(output) }
        else println(output)
    }
    private fun algUnicode() =
        when (config["mode"]) {
            "enc" -> config["data"]!!.map { it + config["key"]!!.toInt() }.joinToString("")
            "dec" -> config["data"]!!.map { it - config["key"]!!.toInt() }.joinToString("")
            else -> throw Exception("Error - Unknown mode: ${config["mode"]}")
        }
    private fun algShift() = buildString {
        codeDict.apply {
            for (ch in 0..25) {
                when (config["mode"]) {
                    "enc" -> {
                        this[ALPHABET_LOW[ch]] = ALPHABET_LOW[(ch + config["key"]!!.toInt()) % 26]
                        this[ALPHABET_UP[ch]] = ALPHABET_UP[(ch + config["key"]!!.toInt()) % 26]
                    }
                    "dec" -> {
                        this[ALPHABET_LOW[(ch + config["key"]!!.toInt()) % 26]] = ALPHABET_LOW[ch]
                        this[ALPHABET_UP[(ch + config["key"]!!.toInt()) % 26]] = ALPHABET_UP[ch]
                    }
                }
            }
        }
        config["data"]!!.forEach { append(if (it in codeDict) codeDict[it] else it) }
    }
}

fun createArgs(): Array<String> {
    var args = arrayOf<String>()
    print("Mode(enc/dec): "); args += "-mode"; args += readln()
    print("Algorithm(Shift/Unicode): "); args+= "-alg"; args += readln()
    print("Data: "); args += "-data"; args += readln()
    print("Key: "); args += "-key"; args += readln()
    return args
}

fun main(args: Array<String>) {
    val programArgs = if (args.isEmpty()) createArgs() else args
    val cryptographer = Cryptographer(programArgs)
    cryptographer.menu()
}