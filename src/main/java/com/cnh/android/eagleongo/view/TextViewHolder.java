package com.cnh.android.eagleongo.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnh.android.eagleongo.R;

/**
 * Created by Hai on 4/22/17.
 */
public class TextViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_view)
    TextView mTextView;
    @BindView(R.id.viewstub_udw)
    FrameLayout udwView;

    TextViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
