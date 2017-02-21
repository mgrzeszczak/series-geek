package mgrzeszczak.com.github.seriesgeek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
import mgrzeszczak.com.github.seriesgeek.model.api.Season;
import mgrzeszczak.com.github.seriesgeek.model.api.Series;
import mgrzeszczak.com.github.seriesgeek.service.ApiService;
import mgrzeszczak.com.github.seriesgeek.service.ProfileService;
import mgrzeszczak.com.github.seriesgeek.view.adapter.ObjectListAdapter;
import mgrzeszczak.com.github.seriesgeek.view.holders.SeasonViewHolder;
import mgrzeszczak.com.github.seriesgeek.view.holders.SeriesViewHolder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Maciej on 21.02.2017.
 */
public class SeriesActivity extends BaseActivity {

    private final static String TAG = SeriesActivity.class.getSimpleName();

    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ObjectListAdapter<Season> seasonListAdapter;
    private int showId;

    @Inject
    ApiService apiService;
    @Inject
    ProfileService profileService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        Injector.INSTANCE.getApplicationComponent().inject(this);
        init();
    }

    private void init(){
        seasonListAdapter = new ObjectListAdapter<>(R.layout.item_series, SeasonViewHolder::new);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(seasonListAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        seasonListAdapter.getPositionClicks().subscribe(s->{
            Intent intent = new Intent(this,SeasonActivity.class);
            intent.putExtra(getString(R.string.show_id),showId);
            intent.putExtra(getString(R.string.season_id),s.getId());
            this.startActivity(intent);
        });

        //if (getSupportActionBar()!=null)
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showId = getIntent().getIntExtra(getString(R.string.show_id),0);
        apiService.getSeriesInfo(showId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(series->{
            name.setText(series.getName());
            description.setText(series.getSummary());
            if (series.getImage()!=null && series.getImage().getOriginal()!=null)
                Picasso.with(poster.getContext()).load(series.getImage().getOriginal()).into(poster);
        });
        apiService.getSeasons(showId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(seasons-> {
            for (Season season : seasons) seasonListAdapter.add(season);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
