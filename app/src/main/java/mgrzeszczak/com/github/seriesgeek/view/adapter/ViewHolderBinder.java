package mgrzeszczak.com.github.seriesgeek.view.adapter;

import rx.subjects.PublishSubject;

/**
 * Created by Maciej on 21.02.2017.
 */
public interface ViewHolderBinder<T> {

    void onBind(BaseHolder holder, T item, PublishSubject<T> onClick, PublishSubject<T> onLongClick);

}
