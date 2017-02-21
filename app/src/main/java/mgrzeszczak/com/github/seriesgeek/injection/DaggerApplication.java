package mgrzeszczak.com.github.seriesgeek.injection;

import android.app.Application;


/**
 * Created by maciek on 20.01.17.
 */

public class DaggerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Injector.INSTANCE.initializeApplicationComponent(this);
    }

}
