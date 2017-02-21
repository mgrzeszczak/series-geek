package mgrzeszczak.com.github.seriesgeek.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
import mgrzeszczak.com.github.seriesgeek.model.Series;
import mgrzeszczak.com.github.seriesgeek.model.SeriesSearchEntity;
import mgrzeszczak.com.github.seriesgeek.service.ApiService;
import mgrzeszczak.com.github.seriesgeek.service.LogService;
import mgrzeszczak.com.github.seriesgeek.view.adapter.SeriesListAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Maciej on 21.02.2017.
 */
public class CardFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private SeriesListAdapter seriesListAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    ApiService apiService;
    @Inject
    LogService logService;

    private int position;

    public static CardFragment newInstance(int position) {
        CardFragment f = new CardFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
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
        seriesListAdapter = new SeriesListAdapter(new ArrayList<Series>(), R.layout.item_series);
        recyclerView.setAdapter(seriesListAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        seriesListAdapter.getPositionClicks().subscribe(s->{
            logService.log(s.toString());
        });

        apiService.searchSeries("flash").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(result->{
            for (SeriesSearchEntity entity : result) seriesListAdapter.add(entity.getSeries());
        });

        return rootView;
    }
}


