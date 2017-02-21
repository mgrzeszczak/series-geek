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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.fragment.CardFragment;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Injector.INSTANCE.getApplicationComponent().inject(this);

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

        private final String[] TITLES = {"Your shows", "Search", "Settings"};

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
