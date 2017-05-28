package base.earthgrabber.network;

import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.jsoup.*;
/**
 * Created by Philip on 24/05/2017.
 */

public class ImageDownloader  extends AsyncTask{
    @Override
    protected Object doInBackground(Object[] objects) {

        /*
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
*/

       // try {
            //URL url = new URL("https://www.reddit.com/r/EarthPorn/top/?sort=top&t=day");
            //DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //DocumentBuilder db = dbf.newDocumentBuilder();
            //Document doc = db.parse(url.openStream());
            Log.d("URL", "starting...");
        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect("https://www.reddit.com/r/EarthPorn/top/?sort=top&t=day").get();
        } catch (IOException e) {
                e.printStackTrace();
        }
        Log.d("URL", "got doc");
           // Elements images = doc.select("div.imageInlineCenter img[src]");
        //Elements images = doc.getElementsByTag("");
        Elements images = doc.getElementsByAttribute("data-url");

                //doc.select("div.node div.content img");

        for (Element e : images){
            Log.d("IMG", e.attr("data-url"));

            //Log.d("IMG", e.attr("src");
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
