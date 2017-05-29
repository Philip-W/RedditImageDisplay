package base.earthgrabber.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Philip on 24/05/2017.
 */

public class ImageDownloader  extends AsyncTask{

    private Context context;
    public ImageDownloader(Context ctx){
            context = ctx;
    }


    /*
     * As I want all image collection to be done away from the ui errors will not be
     * thrown to the above thread and instead will be controlled inside this thread
     */
    private void saveImages(ArrayList<String> list){
        int image_count = 0;
        for (String image_url : list) {
            try {
                URL url = new URL(image_url);
                InputStream in = new BufferedInputStream(url.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();

                String FILENAME = "Image_" + image_count + image_url.substring(image_url.length() -4);

                FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
                fos.write(response);
                fos.close();
                //Log.d("Image save", photo.getAbsolutePath());
                image_count++;
            }
            catch (MalformedURLException e){
                Log.d("IMAGE-ERR", "Error image in url: " + image_url);
                Log.d("IMAGE-ERR", e.getMessage());
            }
            catch (IOException e){
                Log.d("IMAGE-ERR", e.getMessage());
            }
        }
        File f = context.getFilesDir();
        for(File images : f.listFiles()){
            Log.d("IMG", images.getName());
        }



    }

    @Override
    protected Object doInBackground(Object[] objects) {
        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect("https://www.reddit.com/r/EarthPorn/top/?sort=top&t=day").get();
        }
        catch (IOException e) {
                e.printStackTrace();
        }
        if (doc == null){
            Log.d("URL", "Webpage Html Collection failed");
            return null;
        }

        // By reviewing the page source I found that each post is (as expected) contained within it's
        // own element, with each element having a 'data-url' attribute, from what I can tell this
        // is *almost* always the url for the full image, so I'll parse it.
        Elements images = doc.getElementsByAttribute("data-url");

        ArrayList<String> imageURLs = new ArrayList<>();
        String url;
        for (Element e : images){
            //Log.d("IMG", e.attr("data-url"));
            url = e.attr("data-url");

            // Deals with the case where the data might not be a direct link e.g flickr link
            if (url.endsWith(".jpg") || url.endsWith(".png")) {
                imageURLs.add(url);
            }
        }

        if (!imageURLs.isEmpty()){
            saveImages(imageURLs);
        }

        return true;
    }
}
