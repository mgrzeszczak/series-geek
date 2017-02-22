package mgrzeszczak.com.github.seriesgeek.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Maciej on 21.02.2017.
 */
public class ObjectListAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {

    private List<T> items;
    private int itemLayout;
    private final PublishSubject<T> onClickSubject = PublishSubject.create();
    private final PublishSubject<T> onLongClickSubject = PublishSubject.create();
    private ViewHolderProducer<T> viewHolderProducer;

    public ObjectListAdapter(int itemLayout, ViewHolderProducer<T> viewHolderProducer) {
        this.itemLayout = itemLayout;
        this.items = new ArrayList<>();
        this.viewHolderProducer = viewHolderProducer;
    }

    @Override
    public BaseHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return viewHolderProducer.create(v);
    }

    @Override
    public void onBindViewHolder(BaseHolder<T> holder, int position) {
        holder.getViewHolderBinder().onBind(holder,items.get(position),onClickSubject,onLongClickSubject);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(T item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void add(T item) {
        items.add(item);
        notifyItemInserted(items.size()-1);
    }

    public void clear(){
        int size = items.size();
        items.clear();
        notifyItemRangeRemoved(0,size);
    }

    public void remove(T item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public Observable<T> onClick(){
        return onClickSubject.asObservable();
    }

    public Observable<T> onLongClick() {return onLongClickSubject.asObservable();};
}
