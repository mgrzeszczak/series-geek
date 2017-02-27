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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.activity.SeriesActivity;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
import mgrzeszczak.com.github.seriesgeek.model.ProfileData;
import mgrzeszczak.com.github.seriesgeek.model.api.Series;
import mgrzeszczak.com.github.seriesgeek.service.ApiService;
import mgrzeszczak.com.github.seriesgeek.service.LogService;
import mgrzeszczak.com.github.seriesgeek.service.ProfileService;
import mgrzeszczak.com.github.seriesgeek.view.adapter.ObjectListAdapter;
import mgrzeszczak.com.github.seriesgeek.view.holders.SeriesViewHolder;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by Maciej on 21.02.2017.
 */
public class SeriesFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private ObjectListAdapter<Series> seriesListAdapter;
    private final PublishSubject<Void> updateSubject = PublishSubject.create();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @Inject
    ApiService apiService;
    @Inject
    LogService logService;
    @Inject
    ProfileService profileService;

    private int position;

    public static SeriesFragment newInstance(int position) {
        SeriesFragment f = new SeriesFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    private List<Subscription> subscriptions = new ArrayList<>();
    private final Object lock = new Object();

    public void update(ProfileData profileData){
        synchronized(lock){
            if (seriesListAdapter!=null){
                for (Subscription s : subscriptions) if (!s.isUnsubscribed()) s.unsubscribe();
                seriesListAdapter.clear();
                for (Integer id: profileData.getSavedShows()){
                    Subscription subscribe = apiService.getSeriesInfo(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
                        seriesListAdapter.add(result);
                    });
                    subscriptions.add(subscribe);
                }
            }
        }
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

    public Observable<Void> updateEvent(){
        return updateSubject.asObservable();
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
            Intent intent = new Intent(getActivity(),SeriesActivity.class);
            intent.putExtra(getString(R.string.show_id),s.getId());
            getActivity().startActivity(intent);
        });

        seriesListAdapter.onLongClick().subscribe(s->{
            ProfileData profileData = profileService.get(Profile.getCurrentProfile().getId());
            if (profileData.getSavedShows().contains(s.getId()))
            profileData.getSavedShows().remove(s.getId());
            profileService.save(profileData);
            seriesListAdapter.remove(s);
            Toast.makeText(getContext(),s.getName()+" removed from your collection.",Toast.LENGTH_SHORT).show();
        });

        updateSubject.onNext(null);
        return rootView;
    }

}
