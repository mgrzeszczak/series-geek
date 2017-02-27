package mgrzeszczak.com.github.seriesgeek.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.Profile;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.activity.SeriesActivity;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
import mgrzeszczak.com.github.seriesgeek.model.ProfileData;
import mgrzeszczak.com.github.seriesgeek.model.api.Series;
import mgrzeszczak.com.github.seriesgeek.model.api.SeriesSearchEntity;
import mgrzeszczak.com.github.seriesgeek.service.ApiService;
import mgrzeszczak.com.github.seriesgeek.service.LogService;
import mgrzeszczak.com.github.seriesgeek.service.ProfileService;
import mgrzeszczak.com.github.seriesgeek.view.adapter.ObjectListAdapter;
import mgrzeszczak.com.github.seriesgeek.view.holders.SeriesViewHolder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Maciej on 21.02.2017.
 */
public class SearchFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private ObjectListAdapter<Series> seriesListAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    ApiService apiService;
    @Inject
    LogService logService;
    @Inject
    ProfileService profileService;

    private int position;
    private final Object lock = new Object();

    public static SearchFragment newInstance(int position) {
        SearchFragment f = new SearchFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public void search(String query){
        apiService.searchSeries(query).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(result->{
            synchronized(lock){
                seriesListAdapter.clear();
                for (SeriesSearchEntity entity : result) seriesListAdapter.add(entity.getSeries());
            }
        });
    }

    public void add(Series series){
        seriesListAdapter.add(series);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        Injector.INSTANCE.getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card,container,false);
        ButterKnife.bind(this, rootView);
        ViewCompat.setElevation(rootView, 50);

        recyclerView.setHasFixedSize(true);
        seriesListAdapter = new ObjectListAdapter<>(R.layout.item_series, SeriesViewHolder::new);
        recyclerView.setAdapter(seriesListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        seriesListAdapter.onClick().subscribe(s->{
            /*ProfileData profileData = profileService.get(Profile.getCurrentProfile().getId());
            profileData.getSavedShows().add(s.getId());
            profileService.save(profileData);*/
            Intent intent = new Intent(getActivity(),SeriesActivity.class);
            intent.putExtra(getString(R.string.show_id),s.getId());
            getActivity().startActivity(intent);
        });

        seriesListAdapter.onLongClick().subscribe(s->{
            ProfileData profileData = profileService.get(Profile.getCurrentProfile().getId());
            profileData.getSavedShows().add(s.getId());
            profileService.save(profileData);
            Toast.makeText(getContext(),s.getName()+" added to from collection.",Toast.LENGTH_SHORT).show();
        });

        return rootView;
    }
}
