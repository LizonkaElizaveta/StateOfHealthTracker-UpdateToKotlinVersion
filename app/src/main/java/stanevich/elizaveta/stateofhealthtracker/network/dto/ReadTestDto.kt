package stanevich.elizaveta.stateofhealthtracker.network.dto

data class ReadTestDto(
    val audioFile: String = "",
    val originalText: String = "",
    val state: Double = 0.0
) : DataWrapper()