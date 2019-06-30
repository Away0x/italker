package net.away0x.italker.common.app.widget.recycler;

public interface AdapterCallback<T> {

    void update(T data, RecyclerAdapter.ViewHolder<T> holder);

}


