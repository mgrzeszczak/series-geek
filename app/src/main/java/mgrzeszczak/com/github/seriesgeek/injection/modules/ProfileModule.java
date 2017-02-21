package mgrzeszczak.com.github.seriesgeek.injection.modules;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mgrzeszczak.com.github.seriesgeek.service.ProfileService;

/**
 * Created by Maciej on 21.02.2017.
 */
@Module
public class ProfileModule {

    @Singleton
    @Provides
    public ProfileService profileService(Gson gson){
        return new ProfileService(gson);
    }

}
