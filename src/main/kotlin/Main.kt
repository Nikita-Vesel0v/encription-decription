package encryptdecrypt

class Cryptographer(args: List<String>) {
    private lateinit var mode: String // encrypt or decrypt
    private lateinit var data: String
    private lateinit var output: String
    private var key = 0

    init {
        for (i in 0..2) {
            when (args[i * 2]) {
                "-mode" -> mode = args[i * 2 + 1]
                "-data" -> data = args[i * 2 + 1]
                "-key" -> key = args[i * 2 + 1].toInt()
                else -> throw IllegalArgumentException("Invalid input data: ${args.joinToString(" ")}")
            }
        }
    }

    fun menu() {
        output = when (mode.lowercase()) {
            "enc" -> data.map { it + key }.joinToString("")
            "dec" -> data.map { it - key }.joinToString("")
            else -> { println("Unknown action: $mode"); return }
        }
        println(output)
    }
}
fun createArgs(): List<String> = buildList {
    print("Mode: "); add("-mode"); add(readln())
    print("Data: "); add("-data"); add(readln())
    print("Key: "); add("-key"); add(readln())
}

fun main(args: Array<String>) {
    val newArgs = if (args.size == 6) args.toList() else createArgs()
    val cryptographer = Cryptographer(newArgs)
    cryptographer.menu()
}