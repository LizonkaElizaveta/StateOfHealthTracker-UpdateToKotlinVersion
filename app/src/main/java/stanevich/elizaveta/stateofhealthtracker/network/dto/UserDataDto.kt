package stanevich.elizaveta.stateofhealthtracker.network.dto

import stanevich.elizaveta.stateofhealthtracker.home.database.States

data class UserDataDto(
    val state: Int = 0,
    val dyskinesia: Long = 0,
    val pill: Long
) : DataWrapper() {
    companion object {
        fun fromStates(states: States): SendWrapper {
            val data = UserDataDto(states.moodToInt(), states.dyskinesia, states.pill)
            return SendWrapper(timestamp = states.date, data = data)
        }
    }
}