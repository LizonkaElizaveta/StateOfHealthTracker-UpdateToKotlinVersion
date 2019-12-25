package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import java.io.File

class PcmFile {

    fun convertToWav(fileName: String?, pathDirectory: String, sampleRate: Int, channel: Int, format: Int) : Boolean {
        val f1 = File(pathDirectory, "$fileName.pcm")
        val f2 = File(pathDirectory, "$fileName.wav")

        var converterToWav = ConverterPcmToWav()
        converterToWav!!.pcmToWave(f1, f2)

        return f2.exists()
    }

    fun deletePCM(fileName: String?, pathDirectory: String) {
        val file = File(pathDirectory, "$fileName.pcm")
        if (file.exists()) {
            file.delete()
        }
    }
}