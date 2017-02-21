package mgrzeszczak.com.github.seriesgeek.view.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.model.api.Season;
import mgrzeszczak.com.github.seriesgeek.model.api.Series;
import mgrzeszczak.com.github.seriesgeek.view.adapter.BaseHolder;
import mgrzeszczak.com.github.seriesgeek.view.adapter.ViewHolderBinder;
import rx.subjects.PublishSubject;

/**
 * Created by Maciej on 21.02.2017.
 */
public class SeasonViewHolder extends BaseHolder<Season> {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.poster)
    ImageView poster;

    public SeasonViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public ViewHolderBinder<Season> getViewHolderBinder() {
        return (BaseHolder holder, Season item, PublishSubject<Season> publishSubject)->{
            name.setText("Season "+item.getNumber());
            if (item.getImage()!=null && item.getImage().getMedium()!=null)
                Picasso.with(poster.getContext()).load(item.getImage().getMedium()).into(poster);
            getItemView().setTag(item);
            getItemView().setOnClickListener(v -> publishSubject.onNext(item));
        };
    }
}
