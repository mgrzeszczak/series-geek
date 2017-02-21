package mgrzeszczak.com.github.seriesgeek.view.adapter;

import android.view.View;

/**
 * Created by Maciej on 21.02.2017.
 */
public interface ViewHolderProducer<T> {

    BaseHolder<T> create(View itemView);

}
