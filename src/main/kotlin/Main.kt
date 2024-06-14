package encryptdecrypt

class Cryptographer {
    private lateinit var action: String // encrypt or decrypt
    private lateinit var input: String
    private lateinit var output: String
    private var key = 0

    fun menu() {
        action = readln()
        input = readln()
        key = readln().toInt()
        output = when (action.lowercase()) {
            "enc" -> input.map { it + key }.joinToString("")
            "dec" -> input.map { it - key }.joinToString("")
            else -> { println("Unknown action: $action"); return }
        }
        println(output)
    }


}
fun main() {
    val cryptographer = Cryptographer()
    cryptographer.menu()
}