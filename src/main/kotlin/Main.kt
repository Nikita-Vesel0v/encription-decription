package encryptdecrypt

const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

class Cryptographer {
    private var input = ""
    private var output = ""
    private var key = 0
    private var codeDict = emptyMap<Char, Char>()

    fun menu() {
        val action = readln()
        when (action.lowercase()) {
            "enc" -> encryption()
            "dec" -> TODO()
            else -> { println("Unknown action: $action"); return }
        }
        println(output)

    }

    private fun createCodeDict() {
        codeDict = buildMap {
            for (ch in 0..25) {
                this[ALPHABET[ch]] = ALPHABET[(ch + key) % 26]
            }
        }
    }

    private fun encryption() {
        input = readln()
        key = readln().toInt()
        createCodeDict()
        encrypt()
    }

    private fun encrypt() {
        output = buildString {
            input.lowercase().forEach {
                append(if (it in codeDict) codeDict[it] else it)
            }
        }
    }

}
fun main() {
    val cryptographer = Cryptographer()
    cryptographer.menu()
}