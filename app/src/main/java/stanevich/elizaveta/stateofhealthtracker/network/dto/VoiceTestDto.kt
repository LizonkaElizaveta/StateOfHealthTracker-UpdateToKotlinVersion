package stanevich.elizaveta.stateofhealthtracker.network.dto

data class VoiceTestDto(
    val audio: String,
    val amp: Array<Double> = arrayOf()
) : DataWrapper() {
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