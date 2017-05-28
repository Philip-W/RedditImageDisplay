package base.earthgrabber;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        //imageView = new ImageView(this);
        //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setPadding(8, 8, 8, 8);

        //GridView gridview = (GridView) findViewById(R.id.gridview);
        String filePath = getFilesDir().listFiles()[0].getPath();
        Bitmap bmp = BitmapFactory.decodeFile(filePath);
        imageView.setImageBitmap(bmp);


        new  ImageDownloader(this).execute();

    }
}


