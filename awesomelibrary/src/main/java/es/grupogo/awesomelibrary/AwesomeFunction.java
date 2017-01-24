package es.grupogo.awesomelibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jorge_cmata on 20/1/17.
 */

public class AwesomeFunction {

    private static final String TYPE_PICTURE = "picture";
    private static final String TYPE_VIDEO = "video";

    public static ActionBar setupToolbar(AppCompatActivity activity, Toolbar toolbar, String title, @Nullable DrawerLayout drawerLayout) {

        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar!=null) {
            if (title != null) {
                actionBar.setTitle(title);
            }

            if (drawerLayout!=null) {
                ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                        activity,  drawerLayout, toolbar,
                        R.string.open_drawer, R.string.close_drawer
                );
                drawerLayout.addDrawerListener(mDrawerToggle);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
                mDrawerToggle.syncState();
            }
        }

        return actionBar;
    }

    /**
     * dp to pixels converter
     * @param context context of the activity
     * @param dps number of dps to convert
     * @return number of pixels equivalent to the number of dps provided
     */
    public static int dpToPx(Context context, int dps) {
        return Math.round(context.getResources().getDisplayMetrics().density * dps);

    }

    /**
     * Change the background color of one Drawable object provided
     * @param drawable drawable resource
     * @param color desired color
     * @return Drawable object with the color changed
     */
    public static Drawable tintDrawable(Drawable drawable, int color) {

        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        wrappedDrawable = wrappedDrawable.mutate();
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    /**
     * Enable of disable the translucent mode of the status bar
     * @param activity Activity where the status bar is contained
     * @param makeTranslucent boolean to enable or disable the translucent mode
     */
    public static void setStatusBarTranslucent(Activity activity, boolean makeTranslucent) {
        if (Build.VERSION.SDK_INT>19) {
            if (makeTranslucent) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * Share url opening the native android sharing dialog
     * @param activity Current activity
     * @param url url of the picture
     */
    public static void shareUrl(Activity activity, String url) {
        if (url!=null) {
            Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                    .setType("text/plain")
                    .setText(String.format(Locale.US, "%s", url))
                    .getIntent();

            if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(shareIntent);
            }
        }
    }

    /**
     * Get the screen width
     * @param context context of the current activity
     * @return in number of pixels
     */
    public static float getScreenWidthInPixels(Activity context){
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        return outMetrics.widthPixels;
    }


    // region CAMERA MANAGER
    //----------------------------------------------------------------------


    /**
     * This function prepares an intent to take a picture
     * @param context context of the activity
     * @return intent with the extra_output to do the startActivityForResult
     * @throws IOException
     */
    public Intent takePicture(Context context) throws IOException {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = createFile(TYPE_PICTURE);
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

        File photoFile = createFile(TYPE_VIDEO);
        Uri photoURI = FileProvider.getUriForFile(context,
                context.getPackageName(),
                photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        return intent;
    }


    /**
     * This function creates a file form a given type
     * @param type "picture" or "video"
     * @return
     * @throws IOException
     */
    private File createFile(String type) throws IOException {

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

    //----------------------------------------------------------------------
    //endregion

}
