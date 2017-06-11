package base.earthgrabber.ImageHandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Philip on 11/06/2017.
 */

public class BitmapLoader {

    /* Constructor */
    public BitmapLoader(){

    }

    public static Bitmap getScaledBitmapFromFile(String filePath, int reqWidth, int reqHeight){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // Load the bitmap (in decode bounds)
        BitmapFactory.decodeFile(filePath, options);

        // calculate the scaling size
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Load the bitmap fully
        options.inJustDecodeBounds = false;
        Bitmap bMap = BitmapFactory.decodeFile(filePath, options);

        return bMap;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Get actual image size
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;


        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Find the largest power of 2 that maintains height AND width larger than the
            // required width and height
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}
