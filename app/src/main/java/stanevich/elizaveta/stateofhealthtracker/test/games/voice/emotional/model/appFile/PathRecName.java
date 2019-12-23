package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.model.appFile;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import stanevich.elizaveta.stateofhealthtracker.R;

public class PathRecName {
    private static AtomicLong idGenerator = new AtomicLong();
    private Context context;

    public PathRecName(Context context) {
        this.context = context;
    }

    private static long getId() {
        return idGenerator.incrementAndGet();
    }


    public static String getDate() {
        Date dateNow = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
        return simpleDateFormat.format(dateNow);
    }

    public String getNewName() {
        return context.getString(R.string.baseNameForRecordFile)
                + "_0"
                + "_" + getId()
                + "_" + getDate();
    }
}
