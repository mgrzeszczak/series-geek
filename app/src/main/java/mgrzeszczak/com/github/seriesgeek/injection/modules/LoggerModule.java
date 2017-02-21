package mgrzeszczak.com.github.seriesgeek.injection.modules;

import android.content.Context;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mgrzeszczak.com.github.seriesgeek.service.LogService;

/**
 * Created by maciek on 20.01.17.
 */

@Module
public class LoggerModule {

    @Provides
    @Singleton
    public LogService provideLogger(Context context){
        return new LogService(context);
    }
}
