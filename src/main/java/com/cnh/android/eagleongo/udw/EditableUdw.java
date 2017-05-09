package com.cnh.android.eagleongo.udw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.cnh.android.eagleongo.R;

import java.util.UUID;

/**
 * Created by Hai on 4/22/17.
 */
public class EditableUdw extends GenericUdw<EditableUdw> {
    private static final String TAG = EditableUdw.class.getSimpleName();

//    private static final String TAG = EditableUdw.class.getSimpleName();

    protected View mGhostView, mSelectedView, mSwapView, mGroupView, mTabView;

    private ImageView mGroupImage, mTabImage, mSwapImage;

    public UUID editableUUID;

    private DroppableArea mCurrentArea;

    public EditableUdw(String id, GenericUdw<?> el) {
        super(id, el);
        if (el instanceof EditableUdw) {
            this.editableUUID = ((EditableUdw) el).editableUUID;
        }
        else {
            generateEditableUUID();
        }
    }

    public EditableUdw(GenericUdw<?> el) {
        super(el);
        if (el instanceof EditableUdw) {
            this.editableUUID = ((EditableUdw) el).editableUUID;
        }
        else {
            generateEditableUUID();
        }
    }

    private void generateEditableUUID() {
        editableUUID = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof EditableUdw)) return false;

        EditableUdw that = (EditableUdw) o;

        return editableUUID.equals(that.editableUUID);

    }

    @Override
    public int hashCode() {
        return editableUUID.hashCode();
    }

    @Override
    public View getUdwView(final Context context) {
        // Cached view
        if (mUdwView != null) return mUdwView;

//        if (!RunscreenManagerProvider.isUdwRegistered(context, this)) {
            mUdwView = new MockupUdw(this, new Exception("udw not found"), false).getUdwView(context);
            return mUdwView;
//        }
//
//        final LeftAlignedImageView dummy = new LeftAlignedImageView(context);
//
//        UiUtils.executeOnViewReady(dummy, new Runnable() {
//            @Override
//            public void run() {
//                UiUtils.maskUdw(dummy, context);
//            }
//        });
//
//        try {
//            if (widgetInfo != null && widgetInfo.previewIcon != null) {
//                dummy.setImageDrawable(mWmUtils.loadExternalDrawable(
//                        widgetInfo.widgetPackageName, widgetInfo.previewIcon));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mUdwView = dummy;
//
//        return mUdwView;
    }

    @Override
    public View getView(Context context) {
        // Cached view

        if (mView != null) {
            return mView;
        }

        // Inflate editable layout
        mView = LayoutInflater.from(context).inflate(R.layout.view_editable_udw, null);
        setupChildrenViews(context);

        return mView;
    }

    protected void setupChildrenViews(Context context) {
        // Cache children
        mGhostView = mView.findViewById(R.id.ghostView);
        mSelectedView = mView.findViewById(R.id.udwSelectedInd);
        mSwapView = mView.findViewById(R.id.udwSwapInd);
        mSwapImage = (ImageView) mView.findViewById(R.id.udwSwapImg);
        mGroupView = mView.findViewById(R.id.udwGroupInd);
        mGroupImage = (ImageView) mView.findViewById(R.id.udwGroupImg);
        mTabView = mView.findViewById(R.id.udwTabInd);
        mTabImage = (ImageView) mView.findViewById(R.id.udwTabImg);

        View udwStub = mView.findViewById(R.id.udwStub);
        if (udwStub != null) ((FrameLayout) udwStub).addView(getUdwView(context));

        updateChildrenViews();

        resetIndicatorStates();
    }

    @Override
    public String toString() {
        return super.toString() + " editableUUID " + editableUUID;
    }

    /**
     * Set indicators direction based on view's position in the grid.
     */
    public void updateChildrenViews() {
        // Direction of swap indicator
        RelativeLayout.LayoutParams tabParams = (RelativeLayout.LayoutParams) mTabView.getLayoutParams();
        RelativeLayout.LayoutParams groupParams = (RelativeLayout.LayoutParams) mGroupView.getLayoutParams();
        RelativeLayout.LayoutParams groupImgParams = (RelativeLayout.LayoutParams) mGroupImage.getLayoutParams();
        RelativeLayout.LayoutParams swapImgParams = (RelativeLayout.LayoutParams) mSwapImage.getLayoutParams();
        RelativeLayout.LayoutParams swapParams = (RelativeLayout.LayoutParams) mSwapView.getLayoutParams();
        swapParams.removeRule(RelativeLayout.LEFT_OF);
        swapParams.removeRule(RelativeLayout.RIGHT_OF);
        groupParams.removeRule(RelativeLayout.LEFT_OF);
        groupParams.removeRule(RelativeLayout.RIGHT_OF);
        int margin = (int)mSwapView.getResources().getDimension(R.dimen.indicators_margin);
        if (col == 0) {
            // Anchor to left
            swapImgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            swapImgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            swapParams.addRule(RelativeLayout.RIGHT_OF, mTabView.getId());
            swapParams.setMargins(0, margin, margin, margin);
        }
        else {
            // Anchor to right (default)
            swapImgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            swapImgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            swapParams.addRule(RelativeLayout.LEFT_OF, mTabView.getId());
            swapParams.setMargins(margin, margin, 0, margin);
        }

        // Direction of tab indicator
        RelativeLayout.LayoutParams tabImgParams = (RelativeLayout.LayoutParams) mTabImage.getLayoutParams();
        if (col == 0) {
            // Anchor to left
            tabParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            tabParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            tabImgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            tabImgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            tabParams.setMargins(margin, 0, 0, margin);
        }
        else {
            // Anchor to right (default)
            tabParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            tabParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            tabImgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            tabImgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            tabParams.setMargins(0, 0, margin, margin);
        }

        // Direction of group indicator
        if (col == 0) {
            // Anchor to right
            groupParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            groupParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            groupParams.addRule(RelativeLayout.RIGHT_OF, mTabView.getId());
            groupImgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            groupImgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            groupParams.setMargins(0, margin, margin, margin);
        }
        else {
            // Anchor to left (default)
            groupParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            groupParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            groupParams.addRule(RelativeLayout.LEFT_OF, mTabView.getId());
            groupImgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            groupImgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            groupParams.setMargins(margin, margin, 0, margin);
        }
        //mView.setOnDragListener(this);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        if (mSelectedView != null) mSelectedView.setVisibility(selected ? View.VISIBLE : View.GONE);
    }

    public void setSwapIndicator(boolean allowed, boolean visible) {
        // Visible
        if (visible) {
            // Allowed
            mSwapView.setBackgroundResource(allowed ?
                    R.drawable.editable_udw_indicator_swap_allowed_bg :
                    R.drawable.editable_udw_indicator_swap_not_allowed_bg);
            mSwapImage.setImageResource(allowed ?
                    R.drawable.editable_udw_indicator_swap_allowed :
                    R.drawable.editable_udw_indicator_swap_not_allowed);
            mSwapView.setVisibility(View.VISIBLE);
        }
        else {
            // Do not use GONE here, or you'll miss drag events!
            mSwapView.setVisibility(View.INVISIBLE);
        }
    }

    public void setGroupIndicator(boolean allowed, boolean visible) {
        // Visible
        if (visible) {
            // Allowed
            mGroupView.setBackgroundResource(allowed ?
                    R.drawable.editable_udw_indicator_add_allowed_bg :
                    R.drawable.editable_udw_indicator_add_not_allowed_bg);
            mGroupImage.setImageResource(allowed ?
                    R.drawable.editable_udw_indicator_add_allowed :
                    R.drawable.editable_udw_indicator_add_not_allowed);
            mGroupView.setVisibility(View.VISIBLE);
        }
        else {
            // Do not use GONE here, or you'll miss drag events!
            mGroupView.setVisibility(View.INVISIBLE);
        }
    }

    public void setTabIndicator(boolean allowed, boolean visible) {
        // Visible
        if (visible) {
            // Allowed
            mTabView.setBackgroundResource(allowed ?
                    R.drawable.editable_udw_indicator_add_allowed_bg :
                    R.drawable.editable_udw_indicator_add_not_allowed_bg);
            mTabImage.setImageResource(allowed ?
                    R.drawable.editable_udw_indicator_add_allowed :
                    R.drawable.editable_udw_indicator_add_not_allowed);
            mTabView.setVisibility(View.VISIBLE);
        }
        else {
            // Do not use GONE here, or you'll miss drag events!
            mTabView.setVisibility(View.INVISIBLE);
        }
    }

    public void resetIndicatorStates() {
        setSelected(false);
        setSwapIndicator(true, false);
        setGroupIndicator(true, false);
        setTabIndicator(true, false);
        mCurrentArea = null;
    }

    public View getGhostView() {
        return mGhostView;
    }

}
