package agencyvnn.team.beautiful_girlshd.Utils;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import agencyvnn.team.beautiful_girlshd.Activity.MainActivity;
import agencyvnn.team.beautiful_girlshd.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by hiepnt on 05/03/2018.
 */

public class DownLoadImageTask extends AsyncTask<String,Void,Bitmap>{

    private MainActivity activity;
    public  DownLoadImageTask (MainActivity activity){
        this.activity = activity;
    }
        protected Bitmap doInBackground(String...urls){
        String urlOfImage = urls[0];
        Bitmap logo = null;
        try{
            InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
            logo = BitmapFactory.decodeStream(is);
        }catch(Exception e){ // Catch the download exception
            e.printStackTrace();
        }
        return logo;
    }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
    protected void onPostExecute(Bitmap result){

        if(result != null){
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(activity);
            try {
                wallpaperManager.setBitmap(result);
                Toast toast = Toast.makeText(activity, activity.getString(R.string.setting_wallpaper_success), Toast.LENGTH_LONG);
                toast.show();
                activity.loadAdsFull();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
