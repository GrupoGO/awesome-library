package es.grupogo.awesomelibrary;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Carlos Olmedo on 24/1/17.
 */

public class FileManager {


    public static final String TYPE_PICTURE = "picture";
    public static final String TYPE_VIDEO = "video";

    /**
     * This function creates a file form a given type
     * @param type "picture" or "video"
     * @return
     * @throws IOException
     */
    public static File createFile(String type) throws IOException {

        File fileDirectory = null;
        if (type.equals(TYPE_PICTURE)) {
            fileDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else if (type.equals(TYPE_VIDEO)) {
            fileDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        }

        File mediaFile = null;
        String timestamp = new Date().toString();
        if (type.equals(TYPE_PICTURE)) {
            mediaFile = File.createTempFile(String.format(Locale.US, "IMG_%s", timestamp), ".jpg", fileDirectory);
        } else if (type.equals(TYPE_VIDEO)) {
            mediaFile = File.createTempFile(String.format(Locale.US, "VID_%s", timestamp), ".mp4", fileDirectory);
        }

        Log.i("camera", mediaFile.getAbsolutePath());


        return mediaFile;
    }
}
