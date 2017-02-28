package mgrzeszczak.com.github.seriesgeek.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
public class EpisodeActivity extends BaseActivity {

    private final static String TAG = EpisodeActivity.class.getSimpleName();

    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.description)
    WebView description;
    @BindView(R.id.episodeNumber)
    TextView episodeNumber;

    @Inject
    ApiService apiService;
    @Inject
    ProfileService profileService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);
        ButterKnife.bind(this);
        Injector.INSTANCE.getApplicationComponent().inject(this);
        init();
    }

    private void init(){
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        int seasonId = getIntent().getIntExtra(getString(R.string.season_id),0);
        int episodeId = getIntent().getIntExtra(getString(R.string.episode_id),0);
        int showId = getIntent().getIntExtra(getString(R.string.show_id),0);
        apiService.getEpisode(episodeId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(episode->{
            name.setText(episode.getName());
            episodeNumber.setText(String.format("S%02dE%02d", episode.getSeason(),episode.getNumber()));
            description.loadData(episode.getSummary(),"text/html","UTF-8");
            //description.setText(StringFormatter.removeHtmlTags(episode.getSummary()));
            if (episode.getImage()!=null && episode.getImage().getOriginal()!=null)
                Picasso.with(poster.getContext()).load(episode.getImage().getOriginal()).into(poster);
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
