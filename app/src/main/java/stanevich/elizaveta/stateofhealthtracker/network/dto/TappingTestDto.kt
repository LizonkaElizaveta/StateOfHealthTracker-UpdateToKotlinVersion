package stanevich.elizaveta.stateofhealthtracker.network.dto

import stanevich.elizaveta.stateofhealthtracker.test.games.tapping.model.TappingTest

data class TappingTestDto(
    val leftCount: Int = 0,
    val rightCount: Int = 0
) : DataWrapper() {
    companion object {
        fun fromTappingTest(tappingTest: TappingTest): SendWrapper {
            val data = TappingTestDto(
                leftCount = tappingTest.leftCount,
                rightCount = tappingTest.rightCount
            )
            return SendWrapper(
                timestamp = tappingTest.date,
                data = data,
                testType = "tapping"
            )
        }
    }
}