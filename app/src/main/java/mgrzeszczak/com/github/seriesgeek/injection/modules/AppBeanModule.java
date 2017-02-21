package mgrzeszczak.com.github.seriesgeek.injection.modules;

import dagger.Module;
/**
 * Created by maciek on 20.01.17.
 */

@Module(includes = {LoggerModule.class,GsonModule.class,RetrofitModule.class,ProfileModule.class})
public class AppBeanModule {}
