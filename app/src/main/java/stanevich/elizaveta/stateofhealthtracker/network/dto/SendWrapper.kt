package stanevich.elizaveta.stateofhealthtracker.network.dto

data class SendWrapper(
    val userId: Int = 0,
    val timestamp: Long = 0,
    val testType: String? = null,
    val data: DataWrapper = DataWrapper()
)