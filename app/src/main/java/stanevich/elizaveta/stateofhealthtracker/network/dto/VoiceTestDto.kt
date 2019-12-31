package stanevich.elizaveta.stateofhealthtracker.network.dto

data class VoiceTestDto(
    val audioFile: String = "",
    val amp: Array<Double> = arrayOf()
) : DataWrapper()