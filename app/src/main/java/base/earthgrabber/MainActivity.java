package base.earthgrabber;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

        new  ImageDownloader(this).execute();

    }
}


