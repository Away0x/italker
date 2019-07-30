package net.away0x.italker.common.app.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.away0x.italker.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<T>>
    implements View.OnClickListener, View.OnLongClickListener,
    AdapterCallback<T> {

    private final List<T> mDataList;
    private AdapterListener<T> mListener;

    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<T> listener) {
        this(new ArrayList<T>(), listener);
    }

    public RecyclerAdapter(List<T> dataList, AdapterListener<T> listener) {
        mDataList = dataList;
        mListener = listener;
    }

    /**
     * 复写默认的布局类型返回
     * @param position 坐标
     * @return 类型，其实复写后返回的都是 xml 文件的 id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 创建一个 view holder
     * @param parent RecyclerView
     * @param viewType 界面的类型 (约定为 xml 布局的 id)
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 得到 LayoutInflater 用于把 xml 初始化为 View
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // 把 xml id 为 viewType 的文件初始化为一个 root view
        // 自己规定 viewType 就为界面 xml 的 id
        View root = layoutInflater.inflate(viewType, parent, false);
        // 得到 view holder
        ViewHolder<T> holder = onCreateViewHolder(root, viewType);

        // 设置 view 的 tag 为 viewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);

        // 设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);


        // 进行界面注解绑定
        holder.unbinder = ButterKnife.bind(holder, root);
        // 绑定 callback
        holder.callback = this;

        return holder;
    }

    /**
     * 得到布局的类型
     * @param position
     * @param data
     * @return 返回 xml 文件 id，用于创建 view holder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, T data);

    /**
     * 得到一个新的 ViewHolder
     * @param root 根布局
     * @param viewType 布局类型，其实就是 xml 的 id
     * @return ViewHolder
     */
    protected abstract ViewHolder<T> onCreateViewHolder(View root, int viewType);

    /**
     * 绑定数据到 holder 上
     * @param tViewHolder holder
     * @param i 集合数据 index
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<T> tViewHolder, int i) {
        // 得到需要绑定的数据
        T data = mDataList.get(i);
        // 触发 holder 的绑定方法
        tViewHolder.bind(data);
    }

    /**
     * 得到当前集合的数据量
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据并通知
     * @param data
     */
    public void add(T data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一堆数据并通知这段集合更新
     * @param dataList
     */
    public void add(T... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeChanged(startPos, dataList.length);
        }
    }

    /**
     * 插入一堆数据并通知这段集合更新
     * @param dataList
     */
    public void add(Collection<T> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeChanged(startPos, dataList.size());
        }
    }

    /**
     * 删除
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换一个新的集合
     * @param dataList
     */
    public void replace(Collection<T> dataList) {
        mDataList.clear();
        if (dataList == null || dataList.size() == 0) {
            return;
        }

        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void update(T data, ViewHolder<T> holder) {
        int pos = holder.getAdapterPosition(); // 得到当前 viewholder 的坐标
        if (pos >= 0) {
            // 进行数据的移除与更新
            mDataList.remove(pos);
            mDataList.add(pos, data);
            // 通知这个坐标下的数据有更新，触发 onBindViewHolder
            notifyItemChanged(pos);
        }
    }

    @Override
    public void onClick(View view) {
        ViewHolder<T> viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);

        if (mListener != null) {
            // mListener.onItemClick(viewHolder, viewHolder.mData);
            // 得到 view holder 当前对应的适配器中的数据坐标
            int pos = viewHolder.getAdapterPosition();
            mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ViewHolder<T> viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);

        if (mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }

        return false;
    }

    /**
     * 设置适配器的监听
     * @param adapterListener
     */
    public void setListener(AdapterListener<T> adapterListener) {
        mListener = adapterListener;
    }

    /**
     * 自定义监听器
     * @param <T>
     */
    public interface AdapterListener<T> {
        // cell 点击触发
        void onItemClick(RecyclerAdapter.ViewHolder holder, T data);
        // cell 长按触发
        void onItemLongClick(RecyclerAdapter.ViewHolder holder, T data);
    }

    /**
     * 自定义的 ViewHolder
     * @param <T>
     */
    public static abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

        private Unbinder unbinder;
        protected T mData;
        private AdapterCallback<T> callback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         * @param data
         */
        void bind(T data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 绑定数据时的回调
         * @param data
         */
        protected abstract void onBind(T data);

        /**
         * 更新数据
         * @param data
         */
        public void updateData(T data) {
            if (this.callback != null) {
                this.callback.update(data, this);
            }
        }

    }

    /**
     * 对回调接口做一次实现
     * @param <T>
     */
    public static class AdapterListenerImpl<T> implements AdapterListener<T> {

        @Override
        public void onItemClick(RecyclerAdapter.ViewHolder holder, T data) {

        }

        @Override
        public void onItemLongClick(RecyclerAdapter.ViewHolder holder, T data) {

        }
    }
}

