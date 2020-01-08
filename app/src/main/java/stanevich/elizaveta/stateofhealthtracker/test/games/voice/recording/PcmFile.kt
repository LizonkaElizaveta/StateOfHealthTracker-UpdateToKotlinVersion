package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import java.io.File

object PcmFile {

    fun convertToWav(
        fileName: String?,
        pathDirectory: String,
        sampleRate: Int
    ): Boolean {
        val f1 = File(pathDirectory, "$fileName.pcm")
        val f2 = File(pathDirectory, "$fileName.wav")

        val converterToWav = ConverterPcmToWav()
        converterToWav.pcmToWave(f1, f2, sampleRate)

        return f2.exists()
    }

    fun deletePCM(fileName: String?, pathDirectory: String) {
        val file = File(pathDirectory, "$fileName.pcm")
        if (file.exists()) {
            file.delete()
        }
    }
}