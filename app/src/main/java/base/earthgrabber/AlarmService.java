package base.earthgrabber;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Philip on 29/05/2017.
 */

public class AlarmService extends Service {

    ImageDownloadAlarm imageDownloadAlarm = new ImageDownloadAlarm();
    WidgetUpdateAlarm widgetUpdateAlarm = new WidgetUpdateAlarm();

    public void onCreate()
    {
        imageDownloadAlarm.setAlarm(this);
        widgetUpdateAlarm.setAlarm(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        imageDownloadAlarm.setAlarm(this);
        widgetUpdateAlarm.setAlarm(this);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

}
