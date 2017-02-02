package es.grupogo.awesomelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ShareCompat;

import java.util.Locale;

/**
 * Created by Carlos Olmedo on 30/1/17.
 */

public class ShareUtils {


    public static void share(Context context, String text) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

}
