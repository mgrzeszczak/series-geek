package mgrzeszczak.com.github.seriesgeek.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by maciek on 20.01.17.
 */

public class LogService {

    private Context context;
    private String tag;

    public LogService(Context context) {
        this.context = context;
    }

    public void log(String message){
        log(message,Toast.LENGTH_SHORT);
    }

    public void log(String message,int duration){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        Log.d(tag,message);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
