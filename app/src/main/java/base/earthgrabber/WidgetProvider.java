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
import android.util.Log;
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
            final float scale = context.getResources().getDisplayMetrics().density;
            int displayWidth = (int) (300 * scale + 0.5f);
            int displayHeight = (int) (120 * scale + 0.5f);

            Log.d("Widget", "Scaling: " + displayHeight +" | " + displayWidth);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            String filePath = context.getFilesDir().listFiles()[0].getPath();
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
            Log.d("Widget", "Height: " + bMap.getHeight());
                // Decode bitmap with inSampleSize set

                //remoteViews.setBitmap(R.id.imageView, "setImageBitmap", bMap);
            remoteViews.setImageViewBitmap(R.id.imageView, bMap);
            Log.d("Widget", "Bitmapset");

            //Intent intent = new Intent(context, WidgetProvider.class);
            //intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            //intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            //PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
            //        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);

           // Canvas c = new Canvas(bMap);
            //c.drawBitmap(bMap, new Matrix(), null);
            //remoteViews.setImageViewBitmap(R.id.imageView, bMap);


            AppWidgetManager.getInstance(context).updateAppWidget(widgetId, remoteViews);
            //appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.imageView);
            Log.d("Widget", "Intent Loaded");


            //remoteViews.setTextViewText(R.id.textView, number);
            /*
            try {
                String filePath = context.getFilesDir().listFiles()[0].getPath();
                Bitmap bmp = BitmapFactory.decodeFile(filePath);
                remoteViews.setBitmap(R.id.imageView, "", bmp);
            } catch (IndexOutOfBoundsException e) {
                Log.d("MAIN", "Error finding images in file");
                Log.d("MAIN", e.getMessage());
            }


            Intent intent = new Intent(context, WidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
            */
        }
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
