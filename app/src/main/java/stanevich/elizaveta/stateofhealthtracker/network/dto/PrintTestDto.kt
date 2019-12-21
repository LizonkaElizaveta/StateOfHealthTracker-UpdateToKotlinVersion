package stanevich.elizaveta.stateofhealthtracker.network.dto

import stanevich.elizaveta.stateofhealthtracker.test.games.print.model.PrintTest

data class PrintTestDto(
    val erased: Int = 0,
    val originalText: String = "",
    val userText: String = "",
    val time: Long = 0
) : DataWrapper() {
    companion object {
        fun fromPrintTest(printTest: PrintTest): SendWrapper {
            val data = PrintTestDto(
                erased = printTest.erased,
                originalText = printTest.originalText,
                userText = printTest.userText,
                time = printTest.time
            )
            return SendWrapper(
                timestamp = printTest.date,
                data = data,
                testType = "print"
            )
        }
    }
}