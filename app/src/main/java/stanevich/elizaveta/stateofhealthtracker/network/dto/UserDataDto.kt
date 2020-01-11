package stanevich.elizaveta.stateofhealthtracker.network.dto

data class UserDataDto(
    val state: Double = 0.0,
    val dyskinesia: Long = 0,
    val pill: Long = 0
) : DataWrapper()