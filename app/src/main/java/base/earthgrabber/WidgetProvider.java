package base.earthgrabber;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import base.earthgrabber.ImageHandler.BitmapLoader;

/**
 * Created by Philip on 30/05/2017.
 */

public class WidgetProvider extends AppWidgetProvider {


    private static final String SYNC_CLICKED    = "base.earthgrabber.WidgetClick";
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


            Intent clickIntent = new Intent(context, WidgetProvider.class);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, widgetId, clickIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.imageView, pendingIntent);

            //Finally notify the manager to update the widget
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
            imageCycle = (imageCycle + 1) % context.getFilesDir().list().length;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()==null) {
            Bundle extras = intent.getExtras();
            if(extras!=null) {
                int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                final float scale = Resources.getSystem().getDisplayMetrics().density;
                BitmapLoader bmLoader= new BitmapLoader();

                int displayWidth = (int) (250 * scale + 0.5f);
                int displayHeight = (int) (110 * scale + 0.5f);

                String filePath = context.getFilesDir().listFiles()[imageCycle].getPath();
                Bitmap bitmap = bmLoader.getScaledBitmapFromFile(filePath, displayWidth, displayHeight);

                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.widget_main);
                remoteViews.setImageViewBitmap(R.id.imageView, bitmap);
                AppWidgetManager.getInstance(context).updateAppWidget(widgetId, remoteViews);
                imageCycle = (imageCycle + 1) % context.getFilesDir().list().length;

            }
        }
        else {
            super.onReceive(context, intent);
        }
    }


}
