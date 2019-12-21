package stanevich.elizaveta.stateofhealthtracker.network.dto

data class VoiceTestDto(
    val audio: String,
    val amp: Array<Double> = arrayOf()
) : DataWrapper() {
    companion object {
        fun fromVoiceTest(): SendWrapper {
            val data = PrintTestDto()
            return SendWrapper(timestamp = 0, data = data, testType = "voice")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VoiceTestDto

        if (audio != other.audio) return false
        if (!amp.contentEquals(other.amp)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = audio.hashCode()
        result = 31 * result + amp.contentHashCode()
        return result
    }
}