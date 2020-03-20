package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.media.audiofx.NoiseSuppressor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.model.NameFile
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer


class AudioRecording {

    companion object {
        const val AUDIO_SOURCE = MediaRecorder.AudioSource.MIC
        const val SAMPLE_RATE = 44100
        const val CHANNELS = AudioFormat.CHANNEL_IN_MONO
        const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
        const val BUFFER_SIZE_FACTOR = 2
    }

    private val bufferSize = AudioRecord.getMinBufferSize(
        SAMPLE_RATE,
        CHANNELS,
        AUDIO_FORMAT
    ) * BUFFER_SIZE_FACTOR

    private val currentNameFile = NameFile.getName()
    private val directory: DirectoryRecording = DirectoryRecording()

    private val recorder = AudioRecord(
        AUDIO_SOURCE,
        SAMPLE_RATE,
        CHANNELS,
        AUDIO_FORMAT,
        bufferSize
    )
    private var isRecording = false

    fun getFullNameAudioFile(): String {
        return "${directory.fullNameDir}${File.separator}$currentNameFile.wav"
    }

    fun startRecording(): Boolean {
        directory.createRecDirectory()

        enableNoiseSuppressor()

        recorder.startRecording()

        isRecording = true

        CoroutineScope(Dispatchers.IO + Job()).launch {
            convertByteToTempFile()
        }

        return isRecording
    }

    fun stopRecording(): Boolean {
        isRecording = false

        recorder.stop()
        recorder.release()

        return deletePcmFile()
    }

    private fun deletePcmFile(): Boolean {
        if (PcmFile.convertToWav(
                currentNameFile,
                directory.fullNameDir,
                SAMPLE_RATE
            )
        ) {
            PcmFile.deletePCM(currentNameFile, directory.fullNameDir)
            return true
        }
        return false
    }

    private fun enableNoiseSuppressor() {
        val noiseSuppressor: NoiseSuppressor? = null
        if (NoiseSuppressor.isAvailable() && noiseSuppressor == null) {
            NoiseSuppressor.create(recorder.audioSessionId)
        }
    }

    private fun convertByteToTempFile() {
        val tempFile = File(directory.fullNameDir, "$currentNameFile.pcm")

        val byteBuffer = ByteBuffer.allocateDirect(bufferSize)

        FileOutputStream(tempFile).use { outputStream ->
            while (isRecording) {
                val result = recorder.read(byteBuffer, bufferSize)
                if (result < 0) {
                    throw RuntimeException("Reading failed")
                }
                outputStream.write(byteBuffer.array(), 0, bufferSize)
                byteBuffer.clear()
            }
        }
    }
}