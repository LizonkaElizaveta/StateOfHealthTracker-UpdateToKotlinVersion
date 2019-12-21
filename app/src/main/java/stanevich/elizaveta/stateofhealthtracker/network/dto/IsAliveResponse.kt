package stanevich.elizaveta.stateofhealthtracker.network.dto

data class IsAliveResponse(
    val alive: Boolean,
    val host: String,
    val timestamp: Long
)