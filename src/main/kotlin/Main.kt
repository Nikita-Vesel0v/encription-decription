package encryptdecrypt

import java.io.File

class Cryptographer(args: Array<String>) {
    private var mode: String = "enc"
    private var data: String = ""
    private var key: Int = 0
    private var output = ""
    private var outputFilename = ""

    init {
        for (i in 0 until args.size  / 2) {
            when (args[i * 2]) {
                "-mode" -> mode = args[i * 2 + 1]
                "-data" -> data = args[i * 2 + 1]
                "-key" -> key = args[i * 2 + 1].toInt()
                "-in" -> data = File(args[i * 2 + 1]).readText()
                "-out" -> outputFilename = args[i * 2 + 1]
                else -> throw IllegalArgumentException("Error Invalid input data: ${args.joinToString(" ")}")
            }
        }
    }
    fun menu() {
        output = when (mode.lowercase()) {
            "enc" -> data.map { it + key }.joinToString("")
            "dec" -> data.map { it - key }.joinToString("")
            else -> { println("Unknown action: $mode"); return }
        }

        if (outputFilename.isNotEmpty()) File(outputFilename).writeText(output)
        else println(output)
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