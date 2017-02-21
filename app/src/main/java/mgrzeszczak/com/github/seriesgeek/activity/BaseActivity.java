package mgrzeszczak.com.github.seriesgeek.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import javax.inject.Inject;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
import mgrzeszczak.com.github.seriesgeek.service.LogService;


/**
 * Created by maciek on 20.01.17.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    protected LogService logService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationComponent().inject(this);
        logService.setTag(this.getClass().getSimpleName());
    }

}
