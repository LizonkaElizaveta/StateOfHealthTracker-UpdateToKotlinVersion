package stanevich.elizaveta.stateofhealthtracker.network.dto

data class PrintTestDto(
    val erased: Int = 0,
    val originalText: String = "",
    val userText: String = "",
    val time: Double = 0.0
) : DataWrapper()