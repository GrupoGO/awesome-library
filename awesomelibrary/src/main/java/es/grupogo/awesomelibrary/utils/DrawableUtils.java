package es.grupogo.awesomelibrary.utils;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by Carlos Olmedo on 30/1/17.
 */

public class DrawableUtils {

    /**
     * Change the color of one Drawable object provided
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
}
