package base.earthgrabber;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Philip on 30/05/2017.
 */

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        final int count = appWidgetIds.length;

        //Load into 300x 120dp
        Log.d("Widget", "Loading Widdget...");

        for (int i = 0; i < count; i++) {
            final float scale = Resources.getSystem().getDisplayMetrics().density;

            int displayWidth = (int) (250 * scale + 0.5f);
            int displayHeight = (int) (110 * scale + 0.5f);

            Log.d("Widget", "Scaling: " + displayHeight +" | " + displayWidth);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            String filePath = context.getFilesDir().listFiles()[5].getPath();
            BitmapFactory.decodeFile(filePath, options);

            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;
            String imageType = options.outMimeType;
            Log.d("Widget", "Image Size: " + imageHeight + " | " + imageWidth);

            Log.d("Widget", "Options Loaded");
            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_main);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, displayWidth, displayHeight);

            Log.d("Widget", "Widget size calculated");
            options.inJustDecodeBounds = false;
            Bitmap bMap = BitmapFactory.decodeFile(filePath, options);

            //Bitmap pq = Bitmap.createScaledBitmap(bMap, displayWidth, displayHeight, true);

            //bMap = resizeBitmapFitXY(displayWidth, displayHeight, bMap);

            remoteViews.setImageViewBitmap(R.id.imageView, bMap);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
            AppWidgetManager.getInstance(context).updateAppWidget(widgetId, remoteViews);

        }
    }


    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public Bitmap resizeBitmapFitXY(int width, int height, Bitmap bitmap){
        Bitmap background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        float originalWidth = bitmap.getWidth(), originalHeight = bitmap.getHeight();
        Canvas canvas = new Canvas(background);
        float scale, xTranslation = 0.0f, yTranslation = 0.0f;
        if (originalWidth > originalHeight) {
            scale = height/originalHeight;
            xTranslation = (width - originalWidth * scale)/2.0f;
        }
        else {
            scale = width / originalWidth;
            yTranslation = (height - originalHeight * scale)/2.0f;
        }
        Matrix transformation = new Matrix();
        transformation.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                new RectF(0, 0, width, height), Matrix.ScaleToFit.CENTER);
        //transformation.postTranslate(xTranslation, yTranslation);
        //transformation.preScale(scale, scale);


        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, transformation, paint);
        Log.d("Widget", "Bitmap Size: " + background.getHeight() + " | " + background.getWidth());

        return background;
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
