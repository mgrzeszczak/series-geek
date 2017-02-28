package mgrzeszczak.com.github.seriesgeek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
import mgrzeszczak.com.github.seriesgeek.model.api.Episode;
import mgrzeszczak.com.github.seriesgeek.model.api.Season;
import mgrzeszczak.com.github.seriesgeek.service.ApiService;
import mgrzeszczak.com.github.seriesgeek.service.ProfileService;
import mgrzeszczak.com.github.seriesgeek.util.StringFormatter;
import mgrzeszczak.com.github.seriesgeek.view.adapter.ObjectListAdapter;
import mgrzeszczak.com.github.seriesgeek.view.holders.EpisodeViewHolder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Maciej on 21.02.2017.
 */
public class SeasonActivity extends BaseActivity{
    private final static String TAG = SeasonActivity.class.getSimpleName();

    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.description)
    WebView description;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private int showId;
    private int seasonId;
    private ObjectListAdapter<Episode> episodeListAdapter;
    private Season season;

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
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        episodeListAdapter = new ObjectListAdapter<>(R.layout.item_episode, EpisodeViewHolder::new);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(episodeListAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        episodeListAdapter.onClick().subscribe(e->{
            Intent intent = new Intent(this,EpisodeActivity.class);
            intent.putExtra(getString(R.string.show_id),showId);
            intent.putExtra(getString(R.string.season_id),seasonId);
            intent.putExtra(getString(R.string.episode_id),e.getId());
            this.startActivity(intent);
        });

        //if (getSupportActionBar()!=null)
         //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showId = getIntent().getIntExtra(getString(R.string.show_id),0);
        seasonId = getIntent().getIntExtra(getString(R.string.season_id),0);
        apiService.getSeasonInfo(seasonId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(season->{
            this.season = season;
            name.setText("Season "+season.getNumber());
            description.loadData(season.getSummary(),"text/html","UTF-8");
            //description.setText(StringFormatter.removeHtmlTags(season.getSummary()));
            if (season.getImage()!=null && season.getImage().getOriginal()!=null)
                Picasso.with(poster.getContext()).load(season.getImage().getOriginal()).into(poster);
        });
        apiService.getEpisodes(showId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(episodes-> {
            for (Episode episode : episodes) {
                if (episode.getSeason()==season.getNumber()) episodeListAdapter.add(episode);
            }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
