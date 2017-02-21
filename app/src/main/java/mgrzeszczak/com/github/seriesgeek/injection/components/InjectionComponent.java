package mgrzeszczak.com.github.seriesgeek.injection.components;


import mgrzeszczak.com.github.seriesgeek.activity.BaseActivity;
import mgrzeszczak.com.github.seriesgeek.activity.MainActivity;

/**
 * Created by maciek on 20.01.17.
 */

public interface InjectionComponent {

    void inject(MainActivity activity);
    void inject(BaseActivity activity);

}
