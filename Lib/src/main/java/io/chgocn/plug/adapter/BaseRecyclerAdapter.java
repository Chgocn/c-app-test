package io.chgocn.plug.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for RecyclerView
 *
 * @param <T>
 * @author chgocn(chgocn@gmail.com).
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder> {

    protected Context context;
    private int layoutResId;

    protected List<T> items;
    private List<T> empty = new ArrayList<>();

    /**
     * Create adapter
     *
     * @param context     context
     * @param layoutResId
     */
    public BaseRecyclerAdapter(final Context context, final int layoutResId) {
        this(context);
        this.layoutResId = layoutResId;
    }

    public BaseRecyclerAdapter(List<T> items) {
        if (items == null) {
            items = empty;
        }
        this.items = items;
    }

    public BaseRecyclerAdapter(final Context context) {
        this.context = context;

        items = empty;
    }

    public int getLayoutId(int viewType) {
        return this.layoutResId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutId(viewType), viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public abstract void onBindViewHolder(ViewHolder viewHolder, int position);

    @Override
    public long getItemId(final int position) {
        return items.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void addItem(T viewItem) {
        if (items != null) {
            items.add(viewItem);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<T> newData) {
        if (items != null) {
            int start = items.size();
            items.addAll(newData);
            notifyItemRangeInserted(start, items.size() - 1);
        }
    }

    public void replaceAll(List<T> newData) {
        clearAll();
        if (newData == null) {
            newData = new ArrayList<>();
        }
        items = newData;
        notifyItemRangeInserted(0, items.size() - 1);
    }

    public void clearAll() {
        if (items == null) {
            return;
        }
        int size = this.items.size();
        if (size > 0) {
            items = new ArrayList<>();
            this.notifyItemRangeRemoved(0, size);
        }
    }

    protected List<T> getItems() {
        return items;
    }

    public void setItems(final List<T> items) {
        if (items != null) {
            this.items = items;
        } else {
            this.items = empty;
        }
        notifyDataSetChanged();
    }

    public T getItem(final int position) {
        return items.get(position);
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final SparseArray<View> mViews = new SparseArray<>();

        public ViewHolder(View v) {
            super(v);
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public ViewHolder setTag(int viewId, Object tag) {
            getView(viewId).setTag(tag);
            return this;
        }

        public ViewHolder setText(int viewId, CharSequence text) {
            TextView view = getView(viewId);
            view.setText(text);
            return this;
        }

        public ViewHolder setGone(int viewId, boolean gone) {
            View view = getView(viewId);
            if (gone) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
            return this;
        }

        public ViewHolder setImageResource(int viewId, int drawableId) {
            ImageView view = getView(viewId);
            view.setImageResource(drawableId);
            return this;
        }

        public ViewHolder setOnClickListener(int viewId, View.OnClickListener clickListener) {
            getView(viewId).setOnClickListener(clickListener);
            return this;
        }

        public ViewHolder setOnClickListener(int viewId, View.OnClickListener clickListener, Object tag) {
            View v = getView(viewId);
            v.setTag(tag);
            v.setOnClickListener(clickListener);
            return this;
        }

        public ViewHolder setOnItemClickListener(View.OnClickListener clickListener) {
            itemView.setOnClickListener(clickListener);
            return this;
        }

    }

}
