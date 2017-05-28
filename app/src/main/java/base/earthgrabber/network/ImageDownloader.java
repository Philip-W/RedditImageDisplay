package base.earthgrabber.network;

import android.os.AsyncTask;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Philip on 24/05/2017.
 */

public class ImageDownloader  extends AsyncTask{
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

        Elements images = doc.getElementsByAttribute("data-url");


        for (Element e : images){
            Log.d("IMG", e.attr("data-url"));
        }


/*
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
*/
        return true;
    }
}
