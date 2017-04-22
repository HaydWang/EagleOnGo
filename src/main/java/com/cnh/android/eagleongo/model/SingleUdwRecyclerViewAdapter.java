package com.cnh.android.eagleongo.model;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cnh.android.eagleongo.R;
import com.cnh.android.eagleongo.view.RecyclerItemTouchHelperCallback;
import com.cnh.android.eagleongo.view.SingleUdwViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hai on 4/21/17.
 */
public class SingleUdwRecyclerViewAdapter extends RecyclerView.Adapter<SingleUdwViewHolder>
    implements RecyclerItemTouchHelperCallback.ItemTouchHelperAdapter {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    protected List<SingleUdwViewHolder.UdwItem> mData;

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mData,fromPosition,toPosition);
        //notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemDissmiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);

        // TODO: release UDW
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void clearView(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getAdapterPosition() < 0 ) return;

        viewHolder.itemView.setBackgroundColor(
                SingleUdwViewHolder.getBackgroundColor(mContext, mData.get(viewHolder.getAdapterPosition()).type));
    }

    public static class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if(parent.getChildAdapterPosition(view) != 0)
                outRect.top = space;
        }

    }

    public SingleUdwRecyclerViewAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

        mData = new ArrayList<>();
        for (int i=0; i<32; i++) {
            SingleUdwViewHolder.UdwItem item = new SingleUdwViewHolder.UdwItem(context, i);
            mData.add(item);
        }
    }

    @Override
    public SingleUdwViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleUdwViewHolder(mLayoutInflater.inflate(R.layout.single_udw_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(SingleUdwViewHolder holder, int position) {
        holder.udwViewHolder.setBackgroundColor(SingleUdwViewHolder.getBackgroundColor(mContext,
                mData.get(holder.getAdapterPosition()).type));

        ViewGroup parent = (ViewGroup) mData.get(position).udwView.getParent();
        if (parent == holder.udwViewHolder) return;

        View swap = holder.udwViewHolder.getChildAt(holder.udwViewHolder.getChildCount()-1);
        holder.udwViewHolder.removeAllViews();
        if (parent != null) {
            parent.removeView(mData.get(position).udwView);

            if (swap != null) parent.addView(swap);
        }
        holder.udwViewHolder.addView(mData.get(position).udwView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).type.ordinal();
    }
}