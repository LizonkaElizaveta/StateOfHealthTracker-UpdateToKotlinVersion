package stanevich.elizaveta.stateofhealthtracker.network.dto

data class UserDataDto(
    val state: Int = 0,
    val dyskinesia: Long = 0,
    val pill: Long
) : DataWrapper()