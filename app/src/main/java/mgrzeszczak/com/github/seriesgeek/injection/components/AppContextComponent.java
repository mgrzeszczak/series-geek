package mgrzeszczak.com.github.seriesgeek.injection.components;

import android.content.Context;
import android.location.LocationManager;

import mgrzeszczak.com.github.seriesgeek.injection.DaggerApplication;


/**
 * Created by maciek on 20.01.17.
 */

public interface AppContextComponent {
    DaggerApplication application();
    Context applicationContext();
    LocationManager locationManager();
}
