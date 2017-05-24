package base.earthgrabber.network;

import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Philip on 24/05/2017.
 */

public class ImageDownloader  extends AsyncTask{
    @Override
    protected Object doInBackground(Object[] objects) {
        Image image = null;
        byte[] data = {};
        try {

            URL url = new URL("https://i.redd.it/mfqm1x49akgy.jpg");
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int n = 0;
            while(-1!=(n=in.read(buff))){
                out.write(buff, 0, n);
            }
            out.close();
            in.close();
            data = out.toByteArray();

            if (data.length != 0){
                Log.d("INFO", "Success");
            }
        }
        catch (MalformedURLException e){
            Log.d("URL-ERR", e.getMessage());
        }
        catch (IOException e){
            Log.d("URL-ERR", e.getMessage());
        }

        return data;
    }
}
