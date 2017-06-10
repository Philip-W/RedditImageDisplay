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

    static int imageCycle = 0;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        final int count = appWidgetIds.length;
        final float scale = Resources.getSystem().getDisplayMetrics().density;

        int displayWidth = (int) (250 * scale + 0.5f);
        int displayHeight = (int) (110 * scale + 0.5f);

        // Widget size currently set to 250 x 110dp

        for (int i = 0; i < count; i++) {
            String filePath = context.getFilesDir().listFiles()[2].getPath();


            Bitmap bitmap = getScaledBitmapFromFile(filePath, displayWidth, displayHeight);


            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_main);

            remoteViews.setImageViewBitmap(R.id.imageView, bitmap);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
            //imageCycle = (imageCycle + 1) % context.getFilesDir().list().length;
        }
    }

    public static Bitmap getScaledBitmapFromFile(String filePath, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(filePath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        Bitmap bMap = BitmapFactory.decodeFile(filePath, options);

        return bMap;
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
