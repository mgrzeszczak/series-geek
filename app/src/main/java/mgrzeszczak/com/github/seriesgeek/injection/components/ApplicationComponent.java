package mgrzeszczak.com.github.seriesgeek.injection.components;


import javax.inject.Singleton;

import dagger.Component;
import mgrzeszczak.com.github.seriesgeek.injection.modules.AppBeanModule;
import mgrzeszczak.com.github.seriesgeek.injection.modules.AppContextModule;

/**
 * Created by maciek on 20.01.17.
 */

@Singleton
@Component(modules = {
        AppContextModule.class,
        AppBeanModule.class})
public interface ApplicationComponent
        extends AppContextComponent,
        InjectionComponent,
        BeanComponent {}
