package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.audiofx.NoiseSuppressor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;

import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel.appFile.FileRec;
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel.appFile.PathRecName;

public class AudioRecording {
    private static final int sampleRateInHz = 44100;
    private static final int channels = AudioFormat.CHANNEL_IN_MONO;
    private static final int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private static final int bufferSizeFactor = 2;
    private static final int bufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channels, audioFormat) * bufferSizeFactor;

    private boolean isRecording = false;

    private final Context context;
    private FileRec fileRec;
    private PathRecName pathRecName;
    private String curNameFile;
    AudioRecord recorder = null;

    public boolean isRecording() {
        return isRecording;
    }

    public static int getSampleRateInHz() {
        return sampleRateInHz;
    }

    public String getCurNameFile() {
        return curNameFile;
    }

    Thread recordingThread = null;

    public AudioRecording(Context context) {
        this.context = context;

        fileRec = new FileRec(context);
        pathRecName = new PathRecName(context);
    }

    public void stopRecording() {
        if (recorder == null) return;

        isRecording = false;
        recorder.stop();
        recorder.release();
        recorder = null;
        recordingThread = null;

        if (fileRec.saveToWav(curNameFile)) {
            fileRec.deletePCM(curNameFile);
        }
    }

    public boolean startRecording() {
        recorder = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION,
                sampleRateInHz,
                channels,
                audioFormat,
                bufferSize);

        enableNoiseSuppressor();

        recorder.startRecording();
        isRecording = true;

        recordingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //writeAudioDataToFile();
                ConvertToFile();
            }
        }, "AudioRecorder Thread");
        recordingThread.start();
        return isRecording;
    }

    private void enableNoiseSuppressor() {
        int audioSessionId = recorder.getAudioSessionId();
        if(NoiseSuppressor.isAvailable()){
            NoiseSuppressor.create(audioSessionId);
        }
    }

    private void ConvertToFile(){
        if (!fileRec.isExternalStorage()){
            return;
        }

        curNameFile = pathRecName.getNewName();
        File file = new File(FileRec.getPathMainRecFolder(), curNameFile + ".pcm");

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bufferSize);

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            while (isRecording){
                int result = recorder.read(byteBuffer, bufferSize);
                if (result < 0) { throw new RuntimeException("Reading failed"); }

                System.out.println(result);
                outputStream.write(byteBuffer.array(), 0, bufferSize);
                byteBuffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    double writeAudioDataToFile(){
        File file = new File(FileRec.getPathMainRecFolder(), "log.txt");
        double db2 = 0;

        short buffer[] = new short[bufferSize];

        while (isRecording) {
            recorder.read(buffer, 0, bufferSize);
            int amplitude = (buffer[0] & 0xFF) << 8 | buffer[1];
            amplitude = Math.abs(amplitude);

            db2 = 20* Math.log10(amplitude/1.0);
            if (db2 > 0 && db2 < 25000){
                try {
                    //BufferedWriter for performance, true to set append to file flag
                    BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
                    buf.append(String.valueOf(db2));
                    buf.newLine();
                    buf.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return db2;
    }
}
