package stanevich.elizaveta.stateofhealthtracker.network.dto

data class VoiceTestDto(
    val audio: String = "",
    val amp: Array<Double> = arrayOf()
) : DataWrapper()