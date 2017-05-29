package base.earthgrabber;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;

import base.earthgrabber.network.ImageDownloader;


public class MainActivity extends AppCompatActivity {
/*
 The Idea:
    * Pull best images of the day from reddit.com/r/earthporn
    * save them somewhere, periodically
    * display them asa chosen gallery or
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imageview);

        try {
            String filePath = getFilesDir().listFiles()[0].getPath();
            Bitmap bmp = BitmapFactory.decodeFile(filePath);
            imageView.setImageBitmap(bmp);
        }
        catch(IndexOutOfBoundsException e){
            Log.d("MAIN", "Error finding images in file");
            Log.d("MAIN", e.getMessage());
        }

        startService(new Intent(this, AlarmService.class));

        new  ImageDownloader(this).execute();

    }
}


