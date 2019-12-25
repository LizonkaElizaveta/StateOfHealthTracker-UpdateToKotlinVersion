package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import java.io.*

class ConverterPcmToWav {
    private val sampleRate: Int
    private val channel: Int
    private val format: Int

    constructor(sampleRate: Int, channel: Int, format: Int) {
        this.sampleRate = sampleRate
        this.channel = channel
        this.format = format
    }

    @Throws(IOException::class)
    fun pcmToWave(pcmFile: File, waveFile: File) {
        val header = ByteArray(44)
        val data = ByteArray(pcmFile.length().toInt())

        var input: DataInputStream? = DataInputStream(FileInputStream(pcmFile))
        input?.read(data)
        input?.close()

        var output: DataOutputStream? = DataOutputStream(FileOutputStream(waveFile))

        //chunk id
        header[0] = 'R'.toByte()
        header[1] = 'I'.toByte()
        header[2] = 'F'.toByte()
        header[3] = 'F'.toByte()
        //chunk size
        val totalDataLen = data.size + 36
        header[4] = (totalDataLen and 0xff).toByte()
        header[5] = (totalDataLen shr 8 and 0xff).toByte()
        header[6] = (totalDataLen shr 16 and 0xff).toByte()
        header[7] = (totalDataLen shr 24 and 0xff).toByte()
        // format
        header[8] = 'W'.toByte()
        header[9] = 'A'.toByte()
        header[10] = 'V'.toByte()
        header[11] = 'E'.toByte()
        //subchunk 1 id
        header[12] = 'f'.toByte()
        header[13] = 'm'.toByte()
        header[14] = 't'.toByte()
        // subchunk 1 size
        header[15] = ' '.toByte()
        // audio format
        header[16] = format.toByte()
        header[17] = 0
        header[18] = 0
        header[19] = 0
        header[20] = 1
        header[21] = 0
        // channels
        header[22] = channel.toByte()
        header[23] = 0
        // sample rate
        header[24] = (sampleRate and 0xff).toByte()
        header[25] = (sampleRate shr 8 and 0xff).toByte()
        header[26] = (sampleRate shr 16 and 0xff).toByte()
        header[27] = (sampleRate shr 24 and 0xff).toByte()
        // bit rate
        var bitrate = sampleRate * channel * format
        header[28] = (bitrate / 8 and 0xff).toByte()
        header[29] = (bitrate / 8 shr 8 and 0xff).toByte()
        header[30] = (bitrate / 8 shr 16 and 0xff).toByte()
        header[31] = (bitrate / 8 shr 24 and 0xff).toByte()
        // block align
        header[32] = ((channel * format) / 8).toByte()
        header[33] = 0
        // bits sample
        header[34] = 16
        header[35] = 0
        // subchunk 2 id
        header[36] = 'd'.toByte()
        header[37] = 'a'.toByte()
        header[38] = 't'.toByte()
        header[39] = 'a'.toByte()
        // subchunk 2 size
        header[40] = (data.size and 0xff).toByte()
        header[41] = (data.size shr 8 and 0xff).toByte()
        header[42] = (data.size shr 16 and 0xff).toByte()
        header[43] = (data.size shr 24 and 0xff).toByte()

        output!!.write(header, 0, 44)
        output!!.write(data)
        output.close()
    }
}