package mgrzeszczak.com.github.seriesgeek.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
import mgrzeszczak.com.github.seriesgeek.model.Series;
import mgrzeszczak.com.github.seriesgeek.service.LogService;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Maciej on 21.02.2017.
 */
public class SeriesListAdapter extends RecyclerView.Adapter<SeriesListAdapter.ViewHolder> {

    private final PublishSubject<Series> onClickSubject = PublishSubject.create();

    private List<Series> items;
    private int itemLayout;
    @Inject
    LogService logService;

    public SeriesListAdapter(List<Series> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
        Injector.INSTANCE.getApplicationComponent().inject(this);
    }

    public void add(Series item, int position) {
        items.add(position, item);
        logService.log(item.toString());
        notifyItemInserted(position);
    }

    public void add(Series item) {
        items.add(item);
        logService.log(item.toString());
        notifyItemInserted(items.size()-1);
    }

    public void remove(Series item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Series item = items.get(position);
        holder.name.setText(item.getName());
        Picasso.with(holder.poster.getContext()).load(item.getImage().getMedium()).into(holder.poster);
        holder.itemView.setTag(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Observable<Series> getPositionClicks(){
        return onClickSubject.asObservable();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.poster)
        ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
