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
            ConvertToFile()
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

    private fun ConvertToFile() {
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
                outputStream.write(byteBuffer.array(), 0, bufferSize)
                byteBuffer.clear()
            }
        }
    }

    internal fun writeAudioDataToFile(): Double {
        //val file = File(FileRec.getPathMainRecFolder(), "log.txt")
        var db2 = 0.0

        /*val buffer = ShortArray(bufferSize)

        while (isRecording) {
            recorder.read(buffer, 0, bufferSize)
            var amplitude = buffer[0] and 0xFF shl 8 or buffer[1]
            amplitude = Math.abs(amplitude)

            db2 = 20 * Math.log10(amplitude / 1.0)
            if (db2 > 0 && db2 < 25000) {
                try {
                    //BufferedWriter for performance, true to set append to file flag
                    val buf = BufferedWriter(FileWriter(file, true))
                    buf.append(db2.toString())
                    buf.newLine()
                    buf.close()
                } catch (e: IOException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

            }
        }*/

        return db2
    }
}