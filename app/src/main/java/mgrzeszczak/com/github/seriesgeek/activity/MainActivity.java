package mgrzeszczak.com.github.seriesgeek.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.fragment.CardFragment;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
import mgrzeszczak.com.github.seriesgeek.service.ApiService;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    @Inject
    ApiService apiService;

    private CardFragment myShowsFragment;
    private CardFragment searchFragment;
    private MyPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Injector.INSTANCE.getApplicationComponent().inject(this);
        init();
    }

    private void init(){
        tabs.setTextColor(R.color.colorPrimary);
        myShowsFragment = CardFragment.newInstance(0);
        searchFragment = CardFragment.newInstance(1);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private int counter = 0;

            @Override
            public boolean onQueryTextSubmit(String query) {
                logService.log(query);
                pager.setCurrentItem(1,true);
                pagerAdapter.currentFragment.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*counter++;
                if (counter>=3){
                    counter = 0;
                    logService.log(newText);
                    pagerAdapter.currentFragment.search(newText);
                }*/
                return true;
            }
        });

        menu.findItem(R.id.menu_logout).setOnMenuItemClickListener(menuItem -> {
            logService.log("Logout");
            return true;
        });
        menu.findItem(R.id.menu_logout).setOnMenuItemClickListener(menuItem -> {
            logService.log("Settings");
            return true;
        });

        return true;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Your shows","Search"};

        private int position;
        private CardFragment currentFragment;

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
            this.position = position;
            if (position == 0) return myShowsFragment;
            else return searchFragment;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            this.currentFragment = (CardFragment) object;
        }


    }


}
