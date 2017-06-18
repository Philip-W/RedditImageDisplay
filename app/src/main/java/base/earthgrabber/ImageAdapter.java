package base.earthgrabber;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;

import base.earthgrabber.ImageHandler.BitmapLoader;

import static base.earthgrabber.WidgetProvider.imageCycle;

/**
 * Created by Philip on 11/06/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private File[] files;


    public ImageAdapter(Context c) {
        mContext = c;
        files = mContext.getFilesDir().listFiles();
    }

    public int getCount() {
        //return 3;
        return files.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 210));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap bMap = BitmapLoader.getScaledBitmapFromFile(files[position].getPath(),
                500, 210
                );
        imageView.setImageBitmap(bMap);
        return imageView;
    }
}



