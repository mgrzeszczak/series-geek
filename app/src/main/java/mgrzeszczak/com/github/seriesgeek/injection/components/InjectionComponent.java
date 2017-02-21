package mgrzeszczak.com.github.seriesgeek.injection.components;


import mgrzeszczak.com.github.seriesgeek.activity.BaseActivity;
import mgrzeszczak.com.github.seriesgeek.activity.LoginActivity;
import mgrzeszczak.com.github.seriesgeek.activity.MainActivity;
import mgrzeszczak.com.github.seriesgeek.fragment.CardFragment;
import mgrzeszczak.com.github.seriesgeek.view.adapter.SeriesListAdapter;

/**
 * Created by maciek on 20.01.17.
 */

public interface InjectionComponent {

    void inject(MainActivity activity);
    void inject(BaseActivity activity);
    void inject(LoginActivity activity);
    void inject(CardFragment cardFragment);
    void inject(SeriesListAdapter seriesListAdapter);

}
