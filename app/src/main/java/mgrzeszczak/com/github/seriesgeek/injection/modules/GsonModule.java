package mgrzeszczak.com.github.seriesgeek.injection.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Maciej on 21.02.2017.
 */
@Module
public class GsonModule {

    @Provides
    @Singleton
    public Gson gson(){
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    }

}
