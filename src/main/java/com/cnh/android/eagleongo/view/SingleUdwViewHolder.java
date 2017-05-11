package com.cnh.android.eagleongo.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnh.android.eagleongo.R;
import com.cnh.android.eagleongo.udw.UdwView;

import static com.cnh.android.udw.registration.UDW.POSITION_LEFT_BOTTOM;
import static com.cnh.android.udw.registration.UDW.POSITION_RUNSCREEN;

/**
 * Created by Hai on 4/22/17.
 */
public class SingleUdwViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.single_udw_view_holder)
    public LinearLayout udwViewHolder;

    public enum ITEM_TYPE {
        ITEM_TYPE_SINGLE,
        ITEM_TYPE_DOUBLE
    }

    //TODO: move to presenter
    public static class UdwItem {
        int id;
        public ITEM_TYPE type;
        public UdwView udw;
        public View udwView;

        public UdwItem(Context context, int id, String sId, String packageName, String className) {
            this.id = id;
            type = id % 2 == 0 ? ITEM_TYPE.ITEM_TYPE_SINGLE : ITEM_TYPE.ITEM_TYPE_DOUBLE;

            //TODO: change to lazy initial
            udw = new UdwView(sId,
                    packageName,
                    className);
            udwView = udw.getView(context);
            udw.callOnCreate(context);
            udw.callInit(context, POSITION_RUNSCREEN, false);
            //udw.callOnResume(context);
        }
    }

    public SingleUdwViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public static int getBackgroundColor(Context context, ITEM_TYPE type) {
        /*if (type == ITEM_TYPE.ITEM_TYPE_SINGLE) {
            return context.getResources().getColor(R.color.colorPrimary);
        } else if (type == ITEM_TYPE.ITEM_TYPE_DOUBLE)  {
            return context.getResources().getColor(R.color.colorPrimaryDark);
        }*/

        return context.getResources().getColor(android.R.color.transparent);
    }

}