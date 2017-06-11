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

import base.earthgrabber.ImageHandler.BitmapLoader;

/**
 * Created by Philip on 30/05/2017.
 */

public class WidgetProvider extends AppWidgetProvider {

    static int imageCycle = 0;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        final int count = appWidgetIds.length;
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        BitmapLoader bmLoader= new BitmapLoader();

        int displayWidth = (int) (250 * scale + 0.5f);
        int displayHeight = (int) (110 * scale + 0.5f);

        // Widget size currently set to 250 x 110dp

        for (int i = 0; i < count; i++) {
            // Get the file path and load the bitmap
            String filePath = context.getFilesDir().listFiles()[imageCycle].getPath();
            Bitmap bitmap = bmLoader.getScaledBitmapFromFile(filePath, displayWidth, displayHeight);

            // Set the bitmap to the image view of the widget
            int widgetId = appWidgetIds[i];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_main);
            remoteViews.setImageViewBitmap(R.id.imageView, bitmap);

            //Finally notify the manager to update the widget
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
            imageCycle = (imageCycle + 1) % context.getFilesDir().list().length;
        }
    }


}
