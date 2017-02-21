package mgrzeszczak.com.github.seriesgeek.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.fragment.CardFragment;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
import mgrzeszczak.com.github.seriesgeek.model.Episode;
import mgrzeszczak.com.github.seriesgeek.model.Season;
import mgrzeszczak.com.github.seriesgeek.model.Series;
import mgrzeszczak.com.github.seriesgeek.model.SeriesSearchEntity;
import mgrzeszczak.com.github.seriesgeek.service.ApiService;
import mgrzeszczak.com.github.seriesgeek.service.LogService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    /*@BindView(R.id.button)
    Button button;

    @OnClick(R.id.button)
    public void click(){
        startLongRunningOperation().subscribe(s->{
           logService.log(s);
        });
    }*/
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Injector.INSTANCE.getApplicationComponent().inject(this);


    /*
        apiService.getSeriesInfo("tt0944947").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s->{
            logService.log(s.toString());

            apiService.getEpisodes(s.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(ee->{
               for (Episode e : ee){
                   logService.log(e.toString());
               }
            });


            apiService.getSeasons(s.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(ee->{
                for (Season season : ee){
                    logService.log(season.toString());
                }
            });

            apiService.getEpisode(s.getId(),1,1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(epi->{
               logService.log(epi.toString());
            });

        });*/

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        // Attach the view pager to the tab strip
        tabs.setViewPager(pager);
    }

    private Observable<String> startLongRunningOperation(){
        return Observable.fromCallable(this::longRunningOp).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private String longRunningOp(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Complete!";
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Your shows","Shows","Settings"};

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return CardFragment.newInstance(position);
        }
    }
}
