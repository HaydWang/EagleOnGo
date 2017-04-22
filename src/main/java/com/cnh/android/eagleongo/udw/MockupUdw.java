package com.cnh.android.eagleongo.udw;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import android.widget.TextView;
import com.cnh.android.eagleongo.R;
import com.cnh.android.udw.registration.UDW;

import java.util.UUID;

/**
 * Created by Hai on 4/22/17.
 */
public class MockupUdw extends EditableUdw {
    private String mText = "Mockup UDW";

    private class FrameLayoutUdw extends FrameLayout implements UDW {

        public FrameLayoutUdw(Context context) { super(context); }

        @Override
        public void init(Object rsm, String id, String params, int position, boolean grouped, boolean tabbed, String size) { }

        @Override
        public void onCreate(UUID uuid) { }

        @Override
        public void onDestroy() { }

        @Override
        public void onResume() {

        }

        @Override
        public void onPause() {

        }
    }

    private boolean mIsEditMode = false;


    public MockupUdw(GenericUdw<?> el, Exception e, boolean isEditMode) {
        super(el);

        setEditMode(isEditMode);
        setException(e);
    }

    public MockupUdw setText(String text) {
        mText = text;

        return this;
    }

    public void setEditMode(boolean editMode) {
        mIsEditMode = editMode;
        clearView();
    }

    public MockupUdw setException(Exception e) {
//		setText(id + ": " + e.getClass().getSimpleName() + "\n" + e.getMessage());
        setText(id + ":\n" + R.string.item_not_available);
        return this;
    }

    @Override
    public View getView(Context context) {
        if (mView != null) return mView;

        if (mIsEditMode) {
            mView = super.getView(context);
        }
        else {
            mView = new View(context);
            mView.setVisibility(View.GONE);
        }

        return mView;
    }

    @Override
    public View getUdwView(Context context) {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setText(mText);

        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.mockup_udw_bg);

        View returnView = new FrameLayoutUdw(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(2, 2, 2, 2);
        textView.setLayoutParams(params);

        ((FrameLayout)returnView).addView(textView);

        return returnView;
    }
}
