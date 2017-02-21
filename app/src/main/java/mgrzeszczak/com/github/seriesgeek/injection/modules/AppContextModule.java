package mgrzeszczak.com.github.seriesgeek.injection.modules;

import android.content.Context;
import android.location.LocationManager;


import dagger.Module;
import dagger.Provides;
import mgrzeszczak.com.github.seriesgeek.injection.DaggerApplication;

/**
 * Created by maciek on 20.01.17.
 */

@Module() //a module could also include other modules
public class AppContextModule {
    private final DaggerApplication application;

    public AppContextModule(DaggerApplication application) {
        this.application = application;
    }

    @Provides
    public DaggerApplication application() {
        return this.application;
    }

    @Provides
    public Context applicationContext() {
        return this.application;
    }

    @Provides
    public LocationManager locationService(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
}
