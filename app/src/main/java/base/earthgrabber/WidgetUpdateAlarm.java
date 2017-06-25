package base.earthgrabber;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Philip on 25/06/2017.
 */

public class WidgetUpdateAlarm extends BroadcastReceiver {

    private Context context;
    private final int ALARM_ID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        //super.onReceive(context, intent);
        Log.d("ImageDownloadAlarm", "Received");

    }

    public void setAlarm(Context context)
    {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(WidgetProvider.ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(context, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000 * 60  * 30 , pendingIntent); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, WidgetUpdateAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
