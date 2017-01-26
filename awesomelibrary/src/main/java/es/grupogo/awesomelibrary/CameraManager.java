package es.grupogo.awesomelibrary;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Carlos Olmedo on 24/1/17.
 */

public class CameraManager {

    /**
     * This function prepares an intent to take a picture
     * @param context context of the activity
     * @return intent with the extra_output to do the startActivityForResult
     * @throws IOException
     */
    public Intent takePicture(Context context) throws IOException {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = FileManager.createFile(FileManager.TYPE_PICTURE);
        Uri photoURI = FileProvider.getUriForFile(context,
                context.getPackageName(),
                photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        return intent;
    }

    /**
     * This function prepares an intent to take a video
     * @param context context of the activity
     * @return intent with the extra_output to do the startActivityForResult
     * @throws IOException
     */
    public Intent takeVideo(Context context) throws IOException {

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        File photoFile = FileManager.createFile(FileManager.TYPE_VIDEO);
        Uri photoURI = FileProvider.getUriForFile(context,
                context.getPackageName(),
                photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        return intent;
    }
}
