package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.media.audiofx.NoiseSuppressor
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.model.NameFile
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import kotlin.experimental.and
import kotlin.math.abs

class AudioRecording(private var contex: Context?) {
    companion object{
        const val sampleRateInHz = 44100
        const val channels = AudioFormat.CHANNEL_IN_MONO
        const val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        const val bufferSizeFactor = 2
    }

    private var bufferSize : Int = AudioRecord.getMinBufferSize(
        sampleRateInHz,
        channels,
        audioFormat
    ) * bufferSizeFactor

    private var isRecording : Boolean = false

    private var curNameFile : String? = null
    private var directory : DirectoryRecording = DirectoryRecording(contex)

    private var recorder: AudioRecord? = null
    private var recordingThread: Thread? = null

    private var listAmpl: MutableList<Double> = mutableListOf()

    fun getFullNameAudioFile(): String{
        return directory.getFullNameDirectory() +
                File.separator +
                curNameFile
    }

    fun isRecording(): Boolean {
        return isRecording
    }

    fun getSampleRateInHz(): Int {
        return sampleRateInHz
    }

    fun getCurNameFile(): String? {
        return curNameFile
    }

    fun stopRecording() : Boolean {
        if (recorder == null) return false

        isRecording = false
        recorder!!.stop()
        recorder!!.release()
        recorder = null
        recordingThread = null

        return deletePcmFile()
    }

    private fun deletePcmFile() : Boolean{
        var pcmFile = PcmFile()
        if (pcmFile!!.convertToWav(curNameFile, directory.getFullNameDirectory(), sampleRateInHz, channels, audioFormat)){
            pcmFile!!.deletePCM(curNameFile, directory.getFullNameDirectory())
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

        recorder!!.startRecording()
        isRecording = true

        recordingThread = Thread(Runnable {
            convertToFile()
        }, "AudioRecorder Thread")
        recordingThread!!.start()

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
        curNameFile = pathNameFile.getName()

        val file = File(directory.getFullNameDirectory(), "$curNameFile.pcm")

        val byteBuffer = ByteBuffer.allocateDirect(bufferSize)

        FileOutputStream(file).use { outputStream ->
            while (isRecording) {
                val result = recorder!!.read(byteBuffer, bufferSize)
                if (result < 0) {
                    throw RuntimeException("Reading failed")
                }
                addNewAmpl(byteBuffer)
                outputStream.write(byteBuffer.array(), 0, bufferSize)
                byteBuffer.clear()
            }
        }
    }

    private fun addNewAmpl(byteBuffer: ByteBuffer){
        var amplitude = ((byteBuffer[0] and 0xFF.toByte()).toInt() shl 8 or byteBuffer[1].toInt()) / 1.0
        amplitude = abs(amplitude)

        if (amplitude > 0 && amplitude < 25000)
            listAmpl.add(amplitude)
    }
}