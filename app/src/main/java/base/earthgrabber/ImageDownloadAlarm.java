package base.earthgrabber;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import base.earthgrabber.network.ImageDownloader;

/**
 * Created by Philip on 29/05/2017.
 */

public class ImageDownloadAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //super.onReceive(context, intent);
        Log.d("ImageDownloadAlarm", "Received");
        new  ImageDownloader(context).execute();
    }


    public void setAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, ImageDownloadAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000 * 60 * 60 * 24, pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, ImageDownloadAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
