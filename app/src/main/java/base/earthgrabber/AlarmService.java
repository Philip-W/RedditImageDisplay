package base.earthgrabber;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Philip on 29/05/2017.
 */

public class AlarmService extends Service {

    ImageDownloadAlarm imageDownloadAlarm = new ImageDownloadAlarm();
    public void onCreate()
    {
        imageDownloadAlarm.setAlarm(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        imageDownloadAlarm.setAlarm(this);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        imageDownloadAlarm.setAlarm(this);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

}
