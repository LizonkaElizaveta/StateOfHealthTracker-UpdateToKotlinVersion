package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.app.Activity
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.media.audiofx.AudioEffect
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
import android.widget.Toast
import android.content.pm.PackageManager
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import stanevich.elizaveta.stateofhealthtracker.App.Companion.context
import java.util.jar.Manifest


class AudioRecording {

    companion object {
        const val audioSource = MediaRecorder.AudioSource.MIC
        const val sampleRateInHz = 44100
        const val channels = AudioFormat.CHANNEL_IN_MONO
        const val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        const val bufferSizeFactor = 2
    }

    private val bufferSize = AudioRecord.getMinBufferSize(
        sampleRateInHz,
        channels,
        audioFormat
    ) * bufferSizeFactor

    private var currentNameFile = ""
    private var directory: DirectoryRecording = DirectoryRecording()

    private val recorder = AudioRecord(
        audioSource,
        sampleRateInHz,
        channels,
        audioFormat,
        bufferSize
    )
    private var isRecording = false

    val listAmp: MutableList<Double> = arrayListOf()

    fun getFullNameAudioFile(): String {
        return "${directory.fullNameDir}${File.separator}$currentNameFile.wav"
    }

    fun startRecording(): Boolean {
        enableNoiseSuppressor()

        recorder?.let { it.startRecording() }

        isRecording = true

        CoroutineScope(Dispatchers.IO + Job()).launch {
            convertToFile()
        }

        return isRecording
    }

    fun stopRecording(): Boolean {
        if (recorder == null) return false

        isRecording = false

        recorder?.let { it.stop() }
        recorder?.let { it.release() }

        return deletePcmFile()
    }

    private fun deletePcmFile(): Boolean {
        var pcmFile = PcmFile
        if (pcmFile.convertToWav(
                currentNameFile,
                directory.fullNameDir,
                sampleRateInHz
            )
        ) {
            pcmFile.let { it.deletePCM(currentNameFile, directory.fullNameDir) }
            return true
        }
        return false
    }

    private fun enableNoiseSuppressor() {
        var noiseSuppressor: NoiseSuppressor? = null
        if (NoiseSuppressor.isAvailable() && noiseSuppressor == null) {
            noiseSuppressor = NoiseSuppressor.create(recorder!!.audioSessionId)
            //noiseSuppressor.enabled = true
        }
    }

    private fun convertToFile() {
        directory.createRecDirectory()

        currentNameFile = NameFile.getName()

        val file = File(directory.fullNameDir, "$currentNameFile.pcm")

        val byteBuffer = ByteBuffer.allocateDirect(bufferSize)

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

    /*fun requestRecordAudioPermission() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(context as Activity,
                arrayOf(android.Manifest.permission.RECORD_AUDIO), 1)
        }
    }*/
}