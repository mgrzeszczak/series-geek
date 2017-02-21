package mgrzeszczak.com.github.seriesgeek.view.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.model.api.Episode;
import mgrzeszczak.com.github.seriesgeek.model.api.Season;
import mgrzeszczak.com.github.seriesgeek.view.adapter.BaseHolder;
import mgrzeszczak.com.github.seriesgeek.view.adapter.ViewHolderBinder;
import rx.subjects.PublishSubject;

/**
 * Created by Maciej on 21.02.2017.
 */
public class EpisodeViewHolder extends BaseHolder<Episode> {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.poster)
    ImageView poster;

    public EpisodeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public ViewHolderBinder<Episode> getViewHolderBinder() {
        return (BaseHolder holder, Episode item, PublishSubject<Episode> publishSubject)->{
            name.setText(item.getName());
            if (item.getImage()!=null && item.getImage().getMedium()!=null)
                Picasso.with(poster.getContext()).load(item.getImage().getMedium()).into(poster);
            getItemView().setTag(item);
            getItemView().setOnClickListener(v -> publishSubject.onNext(item));
        };
    }

}
