package mgrzeszczak.com.github.seriesgeek.injection.modules;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mgrzeszczak.com.github.seriesgeek.service.ApiService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Maciej on 21.02.2017.
 */
@Module
public class RetrofitModule {

    @Provides
    @Singleton
    public ApiService retrofit(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ApiService.ENDPOINT)
                .build();
        return retrofit.create(ApiService.class);
    }

}
