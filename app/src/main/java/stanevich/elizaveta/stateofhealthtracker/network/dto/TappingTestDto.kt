package stanevich.elizaveta.stateofhealthtracker.network.dto

data class TappingTestDto(
    val leftCount: Int = 0,
    val rightCount: Long = 0
) : DataWrapper()