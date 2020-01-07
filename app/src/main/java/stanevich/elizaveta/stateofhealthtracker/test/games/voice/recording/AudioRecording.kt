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
import kotlin.experimental.and
import kotlin.math.abs

class AudioRecording {
    companion object {
        const val sampleRateInHz = 44100
        const val channels = AudioFormat.CHANNEL_IN_MONO
        const val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        const val bufferSizeFactor = 2
    }

    private var bufferSize: Int = AudioRecord.getMinBufferSize(
        sampleRateInHz,
        channels,
        audioFormat
    ) * bufferSizeFactor

    private var isRecording = false

    private var currentNameFile: String? = null
    private var directory: DirectoryRecording = DirectoryRecording()

    private var recorder: AudioRecord? = null

    private val listAmp: MutableList<Double> = arrayListOf()

    fun getAmplitudes(): MutableList<Double> {
        return listAmp
    }

    fun getFullNameAudioFile(): String {
        return "${directory.fullNameDir}${File.separator}$currentNameFile.wav"
    }

    fun stopRecording(): Boolean {
        if (recorder == null) return false

        isRecording = false
        recorder?.let { it.stop() }
        recorder?.let { it.release() }
        recorder = null

        return deletePcmFile()
    }

    private fun deletePcmFile(): Boolean {
        var pcmFile = PcmFile
        if (pcmFile!!.convertToWav(
                currentNameFile,
                directory.fullNameDir,
                sampleRateInHz
            )
        ) {
            pcmFile?.let { it.deletePCM(currentNameFile, directory.fullNameDir) }
            return true
        }
        return false
    }

    fun startRecording(): Boolean {
        recorder = AudioRecord(
            MediaRecorder.AudioSource.VOICE_RECOGNITION,
            sampleRateInHz,
            channels,
            audioFormat,
            bufferSize
        )

        enableNoiseSuppressor()

        recorder?.let { it.startRecording() }
        isRecording = true

        CoroutineScope(Dispatchers.IO + Job()).launch {
            convertToFile()
        }

        return isRecording
    }

    private fun enableNoiseSuppressor() {
        val audioSessionId = recorder!!.audioSessionId
        if (NoiseSuppressor.isAvailable()) {
            NoiseSuppressor.create(audioSessionId)
        }
    }

    private fun convertToFile() {
        directory.createRecDirectory()

        var pathNameFile = NameFile()
        currentNameFile = pathNameFile.getName()

        val file = File(directory.fullNameDir, "$currentNameFile.pcm")

        val byteBuffer = ByteBuffer.allocateDirect(bufferSize)

        addNewAmp(byteBuffer)

        FileOutputStream(file).use { outputStream ->
            while (isRecording) {
                val result = recorder!!.read(byteBuffer, bufferSize)
                if (result < 0) {
                    throw RuntimeException("Reading failed")
                }
                addNewAmp(byteBuffer)
                outputStream.write(byteBuffer.array(), 0, bufferSize)
                byteBuffer.clear()
            }
        }
    }

    private fun addNewAmp(byteBuffer: ByteBuffer) {
        var amplitude =
            ((byteBuffer[0] and 0xFF.toByte()).toInt() shl 8 or byteBuffer[1].toInt()) / 1.0
        amplitude = abs(amplitude)

        if (amplitude > 0 && amplitude < 25000)
            listAmp.add(amplitude)
    }
}