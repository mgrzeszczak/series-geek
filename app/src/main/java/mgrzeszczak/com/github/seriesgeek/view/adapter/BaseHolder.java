package mgrzeszczak.com.github.seriesgeek.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Maciej on 21.02.2017.
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

    private View itemView;

    public BaseHolder(View itemView){
        super(itemView);
        this.itemView = itemView;
    }

    public View getItemView() {
        return itemView;
    }

    public abstract ViewHolderBinder<T> getViewHolderBinder();
}
