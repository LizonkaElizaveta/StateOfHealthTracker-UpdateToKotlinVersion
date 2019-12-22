package stanevich.elizaveta.stateofhealthtracker.network.dto

data class MissClickDto(
    val count: Int = 0,
    val distance: Double = 0.0
) : DataWrapper()