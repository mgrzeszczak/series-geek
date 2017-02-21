package mgrzeszczak.com.github.seriesgeek.injection;


import mgrzeszczak.com.github.seriesgeek.injection.components.ApplicationComponent;
import mgrzeszczak.com.github.seriesgeek.injection.components.DaggerApplicationComponent;
import mgrzeszczak.com.github.seriesgeek.injection.modules.AppContextModule;

/**
 * Created by maciek on 20.01.17.
 */

public enum Injector {

    INSTANCE;

    private ApplicationComponent applicationComponent;

    private Injector(){

    }

    void initializeApplicationComponent(DaggerApplication daggerApplication){
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .appContextModule(new AppContextModule(daggerApplication))
                .build();
        this.applicationComponent = applicationComponent;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
