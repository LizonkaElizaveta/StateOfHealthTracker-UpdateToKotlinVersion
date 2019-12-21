package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel.appFile;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import stanevich.elizaveta.stateofhealthtracker.R;

public class FileRec{
    private final Context context;
    private static String pathMainRecFolder;

    public FileRec(Context context) {
        this.context = context;
        boolean good = createMainDirectory();
    }

    public static String getPathMainRecFolder() {
        return pathMainRecFolder;
    }

    private boolean createMainDirectory(){
       if (isExternalStorage()){
           File file = new File(Environment.getExternalStorageDirectory(),
                   context.getResources().getString(R.string.nameMainFolder));

           pathMainRecFolder = Environment.getExternalStorageDirectory()
                   + File.separator
                   +  context.getResources().getString(R.string.nameMainFolder);

           return file.mkdirs();
       }
       return false;
    }

    public boolean isExternalStorage() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean saveToWav(String fileName){
        File f1 = new File(pathMainRecFolder, fileName + ".pcm");
        File f2 = new File(pathMainRecFolder, fileName + ".wav");

        boolean save = false;
        try {
            toWav wav = new toWav();
            wav.pcvToWave(f1, f2);
            save = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return save;
    }

    public void deletePCM(String fileName){
        File file =new File(pathMainRecFolder, fileName + ".pcm");
        if (file.exists()) { file.delete(); }
    }
}
