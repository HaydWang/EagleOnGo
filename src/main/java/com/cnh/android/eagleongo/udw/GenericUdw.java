package com.cnh.android.eagleongo.udw;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Hai on 4/21/17.
 */

public abstract class GenericUdw<T> {

    private static final String TAG = GenericUdw.class.getSimpleName();
    public static String ID_PREFIX = "UDW";

    public enum DroppableArea { SWAP, GROUP, TAB };

    //protected WmUtils mWmUtils;

    public interface OnDropListener {
        public void onElementDropped(GenericUdw<?> el, DroppableArea area);
    }

    public enum RunningState {
        CREATED, INITED, RESUMED, PAUSED, DESTROYED
    }

    public RunningState state = null;

    protected View mView, mUdwView;
    private ContentValues cv = new ContentValues();

    protected OnDropListener mDropListener;

    // Unique ID
    public final String id;
    protected UUID uuid;
    public final WidgetInfo widgetInfo;

    // Stuff to instantiate UDW
    public final String packageName, className;
    public final String initParams;

    // UDW size
    protected int width;
    public int height;

    // Runtime props
    protected int row, col, tab, page, isgroup;

    //protected GroupUdw group;
    protected String title = "";
    //protected Layout layout;

    protected boolean mSelected;
    protected boolean mMaximized;
    protected boolean mCreated = false;

    public GenericUdw() {
        id = "Mock UDW";
        uuid = UUID.randomUUID();
        widgetInfo = null;
        packageName = "Mock.packageName";
        className = "Mock.className";
        initParams = "Mock.initParams";
    }

    /**
     * UDW from existing element.
     */
    public GenericUdw(GenericUdw<?> el) {
        this(el.id, el);
    }

    /**
     * UDW from existing element.
     */
    public GenericUdw(String id, GenericUdw<?> el) {
        this(id, el.packageName, el.className, el.initParams, el.widgetInfo, el.getWidth(), el.getHeight());
        this.title = el.title;
        this.uuid = el.uuid;
        //setPosition(el.getGroup(), el.getCol(), el.getRow(), el.getTab());

        //mWmUtils = WmUtils.getInstance();
    }

    /**
     * Generic constructor.
     */
    public GenericUdw(String id, String packageName, String className, String initParams,
                      WidgetInfo widgetInfo, int width, int height) {
        this.id = id;
        this.widgetInfo = widgetInfo;
        this.packageName = packageName;
        this.className = className;
        this.width = width;
        this.height = height;
        this.initParams = initParams;
        this.tab = 0;
        this.page = 0;
        //this.layout = layout;

        //mWmUtils = WmUtils.getInstance();
    }

    /**
     * UDW from content provider.
     */
    public GenericUdw(String id, WidgetInfo widgetInfo) {
        this.id = id;
        this.widgetInfo = widgetInfo;
        this.packageName = widgetInfo.widgetPackageName;
        this.className = widgetInfo.widgetClassName;
        this.initParams = widgetInfo.initParams;
        this.width = widgetInfo.width;
        this.height = widgetInfo.height;
        this.tab = 0;
        this.page = 0;
        //this.layout = layout;

        //mWmUtils = WmUtils.getInstance();
    }

//    public T setPosition(GroupUdw group, int col, int row, int tab) {
//        this.group = group;
//        this.col = col;
//        this.row = row;
//        this.tab = tab;
//
//        return (T) this;
//    }

//    public T setPosition(GroupUdw group, int col, int row) {
//        return setPosition(group, col, row, 0);
//    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isMaximized() {
        return mMaximized;
    }

    public void setMaximized(boolean maximized) {
        mMaximized = maximized;
    }

    /**
     *  this is needed by GroupUdw in tabbed mode
     *  in the 8 inches. Because it takes a grid space
     *  more than it normally would do. For every other
     *  udw this is equivalent to getHeight()
     */
    public int getDisplayHeight() {
        return getHeight();
    }

    public String getSize() {
        return width+"x"+height;
    }

//    public void setGroup(GroupUdw group) {
//        this.group = group;
//    }
//
//    public GroupUdw getGroup() {
//        return group;
//    }

    public int getTab() {
        return tab;
    }

    @SuppressWarnings("unchecked")
    public T setTab(int tab) {
        this.tab = tab;

        return (T) this;
    }

    //	public void setTitle(String title) {
//		this.title = title;
//	}
//
    public String getTitle() {
        return title;
    }

//    public int getPage() {
//        if (getGroup() != null) {
//            return getGroup().getPage();
//        }
//        return page;
//    }

    public void setPage(int page) {
        this.page = page;
    }

//    public Layout getLayout() {
//        return layout;
//    }

//    public void setLayout(Layout layout) {
//        this.layout = layout;
//    }

    /**
     * The UDW view loaded from external context.
     */
    public abstract View getUdwView(Context context);

    /**
     * The UDW view framed with indicators.
     * @param context
     * @return
     */
    public abstract View getView(Context context);

    /**
     * Test if two elements overlaps.
     */
    public static boolean overlapsWith(GenericUdw<?> el1, GenericUdw<?> el2, boolean checkGroupChildren) {
        return overlapsWith(el1, el2, checkGroupChildren, null);
    }

    /**
     * Test if two elements overlaps.
     */
    public static boolean overlapsWith(GenericUdw<?> el1, GenericUdw<?> el2, boolean checkGroupChildren, List<GenericUdw<?>> excluded) {
        if (el1.equals(el2)) return false;

        if (excluded == null) excluded = new ArrayList<GenericUdw<?>>();

//        if (checkGroupChildren && el2 instanceof GroupUdw) {
//            // Check if it overlaps with any child
//            GroupUdw group = (GroupUdw) el2;
//            for (GenericUdw<?> child: group.getChildren()) {
//                // Only count visible child for tabbed groups!
//                if (!excluded.contains(child) && group.isChildVisible(child) && overlapsWith(el1, child, false, excluded)) {
//                    return true;
//                }
//            }
//
//            return false;
//        }

        if (el1.getTab() != el2.getTab()) return false;

//        int groupCol = 0, groupRow = 0; GroupUdw group = el2.getGroup();
//        if (group != null) { groupCol = group.getCol(); groupRow = group.getRow();  }
//        int offsetCol = 0, offsetRow = 0; GroupUdw offsetGroup = el1.group;
//        if (offsetGroup != null) { offsetCol = offsetGroup.getCol(); offsetRow = offsetGroup.getRow();  }

        // Example of programmer's lazyness...
//        Rect thisRect = new Rect(el1.getCol()+offsetCol, el1.getRow()+offsetRow, el1.getCol()+offsetCol+el1.getWidth(), el1.getRow()+offsetRow+el1.getDisplayHeight());
//        Rect elRect = new Rect(el2.getCol()+groupCol, el2.getRow()+groupRow, el2.getCol()+groupCol+el2.getWidth(), el2.getRow()+groupRow+el2.getDisplayHeight());
//        boolean overlaps = Rect.intersects(thisRect, elRect);


        return false;//overlaps;
    }

    public static boolean overlapsWithAny(GenericUdw<?> element, List<GenericUdw<?>> elements) {
        return overlapsWithAny(element, elements, new ArrayList<GenericUdw<?>>());
    }

    /**
     * Test if this element overlaps with ANY of the given elements.
     */
    public static boolean overlapsWithAny(GenericUdw<?> element, List<GenericUdw<?>> elements, List<GenericUdw<?>> excluded) {
        if (excluded == null) excluded = new ArrayList<GenericUdw<?>>();

        for (GenericUdw<?> el: elements) if (!excluded.contains(el) && overlapsWith(element, el, true, excluded)) {
            return true;
        }

        return false;
    }

    public boolean fitsInGrid(int gridCols, int gridRows) {
        return getCol() + getWidth() <= gridCols && getRow() + getDisplayHeight() <= gridRows;
    }

    public void setOnDropListener(OnDropListener listener) {
        mDropListener = listener;
    }

//    @Override
//    public String toString() {
//        return this.getClass().getSimpleName()+"/"+ id+": "+getWidth()+"X"+getTrimmedHeight()
//                +" @("+getCol()+","+getRow()+") #"+getTab()+":"+getPage() + " isGroup="+isgroup
//                + " hasGroup: " + (getGroup() != null ? getGroup().id : "null") + " - UUID "+uuid;
//    }

    public int getTrimmedHeight() {
        return getHeight();
    }

    public void clearView() {
        if (mView != null) {
//            View udwStub = mView.findViewById(R.id.udwStub);
//            if (udwStub != null) {
//                ((ViewGroup)udwStub).removeAllViews();
//            }
//            if (mWmUtils == null) {
//                mWmUtils = WmUtils.getInstance();
//            }
//            if (widgetInfo != null && widgetInfo.widgetPackageName != null) {
//                mWmUtils.clearClassesCache(widgetInfo.widgetClassName);
//                mWmUtils.clearContextsCache(widgetInfo.widgetPackageName);
//            }
        }
        onDetachedFromView();
    }

    public void onDetachedFromView() {
        // Re-initialize elements on page change
        mView = null;
        mUdwView = null;
    }

//	public void onAttachedToView() {
//
//	}

    /**
     * Serialize element for DB persistence.
     */
    public ContentValues toContentValues() {
        cv.clear();
//        cv.put(COL_UDW_ID, id != null ? id : "");
//        cv.put(COL_UDW_UUID, uuid != null ? uuid.toString() : "");
//        cv.put(COL_UDW_TITLE, title != null ? title : "");
//        cv.put(COL_UDW_WIDTH, getWidth());
//        cv.put(COL_UDW_HEIGHT, getHeight());
//        cv.put(COL_UDW_ROW, getRow());
//        cv.put(COL_UDW_COL, getCol());
//        cv.put(COL_UDW_TAB, getTab());
//        cv.put(COL_UDW_IS_GROUP, getGroupDbValue());
//        cv.put(COL_UDW_GROUP, getGroup() != null && getGroup().id != null ? getGroup().id : "");
//        cv.put(COL_UDW_HOSTVIEW, getPage());
//        cv.put(COL_UDW_LAYOUT, getLayout() != null && getLayout().getId() != null ? getLayout().getId() : "");

        return cv;
    }

    public int getGroupDbValue() {
        return 0;
    }

//    public static synchronized GenericUdw<?> fromContentValues(Map<String, WidgetInfo> elements, Map<String, GroupUdw> groups,
//                                                               Layout currentLayout, boolean isEditMode, ContentValues cv) {
//        // Base info
//        String id = cv.getAsString(COL_UDW_ID);
//        WidgetInfo i = elements.get(id);
//
//        // Create UDW
//        GenericUdw<?> el = null;
//        GroupUdw group = null;
//        int width = cv.getAsInteger(COL_UDW_WIDTH);
//        int height = cv.getAsInteger(COL_UDW_HEIGHT);
//        int isgroup = cv.getAsInteger(COL_UDW_IS_GROUP);
//        if (isgroup != 0) {
//            // Create group, should not fail
//            try {
//                Class<? extends GenericUdw<?>> clazz = isEditMode ? GroupUdw.class : GroupDisplayUdw.class;
//                el = (GenericUdw<?>) clazz.getConstructor(int.class, int.class, Layout.class).newInstance(width, height, currentLayout);
//            }
//            catch (Exception e) {
//                //Log.v(TAG, e.getClass().getSimpleName()+": "+e.getMessage(), e);
//                e.printStackTrace();
//                el = new MockupUdw(id, width, height, currentLayout, new Exception("Udw not found"), isEditMode);
//            }
//            el.title = cv.getAsString(COL_UDW_TITLE);
//
//            // Restore tabs
//            ((GroupUdw) el).mIsTabbed = isgroup == 2 ? GroupUdw.Direction.LEFT : GroupUdw.Direction.RIGHT;
//        }
//        else {
//            // Create single UDW, can fail if not available
//            try {
//                Class<? extends GenericUdw<?>> clazz = isEditMode ? EditableUdw.class : DisplayUdw.class;
//                el = (GenericUdw<?>) clazz.getConstructor(String.class, WidgetInfo.class, Layout.class).newInstance(id, i, currentLayout);
//                el.width = width; el.height = height;
//            }
//            catch (Exception e) {
//                //Log.v(TAG, e.getClass().getSimpleName()+": "+e.getMessage(), e);
//                el = new MockupUdw(id, width, height, currentLayout, new Exception("Udw not found"), isEditMode);
//            }
//
//            // Set group
//            String groupId = cv.getAsString(COL_UDW_GROUP);
//            group = groups != null && groupId != null ? groups.get(groupId) : null;
//        }
//        el.uuid = UUID.fromString(cv.getAsString(COL_UDW_UUID));
//
//        // Set size and position
//        el.setPosition(group, cv.getAsInteger(COL_UDW_COL), cv.getAsInteger(COL_UDW_ROW), cv.getAsInteger(COL_UDW_TAB));
//        el.setPage(cv.getAsInteger(COL_UDW_HOSTVIEW));
//        el.isgroup = isgroup;
//
//        return el;
//    }

    public void callOnCreate(Context context) {
        if (state == null) {
            //RunscreenManagerProvider.onCreateUdw(context, this);
            state = RunningState.CREATED;
//            UdwFrameLayout layout = ((UdwFrameLayout)getView(context).findViewById(R.id.udwStub));
//            if (layout != null) {
//                layout.setRunningState(state);
//            }
        }
    }

    public void callOnDestroy(Context context) {
        if (state == null) return;
        switch (state) {
            case CREATED:
                break;
            case RESUMED:
                callOnPause(context);
                break;
            case PAUSED:
                break;
            case DESTROYED:
                return;
        }
//		if (D) Log.d(TAG, "callOnDestroy " + this.id + " " + hashCode());
        //RunscreenManagerProvider.onDestroyUdw(context, this);
        state = RunningState.DESTROYED;
//        UdwFrameLayout layout = ((UdwFrameLayout)getView(context).findViewById(R.id.udwStub));
//        if (layout != null) {
//            layout.setRunningState(state);
//        }
    }

    public void callInit(Context context, int position, boolean maximized) {
        //RunscreenManagerProvider.initUdw(rsm, context, position, this, maximized);
        state = RunningState.INITED;
    }

    public void callOnResume(Context context) {
        if (state == null) return;
        switch (state) {
            case CREATED:
                return;
            case INITED:
                break;
            case RESUMED:
                return;
            case PAUSED:
                break;
            case DESTROYED:
                return;
        }
        //RunscreenManagerProvider.onResumeUdw(context, this);
        state = RunningState.RESUMED;
        //UdwFrameLayout layout = ((UdwFrameLayout)getView(context).findViewById(R.id.udwStub));
        //if (layout != null) {
        //    layout.setRunningState(state);
        //}
    }

    public void callOnPause(Context context) {
        if (state == null) return;
        switch (state) {
            case CREATED:
                return;
            case RESUMED:
                break;
            case PAUSED:
                return;
            case DESTROYED:
                return;
        }
        //RunscreenManagerProvider.onPauseUdw(context, this);
        state = RunningState.PAUSED;
        //UdwFrameLayout layout = ((UdwFrameLayout)getView(context).findViewById(R.id.udwStub));
        //if (layout != null) {
        //    layout.setRunningState(state);
        //}
    }

    public Parcelable callOnPrint(Context context) {

        return null;//RunscreenManagerProvider.onPrintUdw(context, this);
    }

    public int getTabCount() {
        return 0;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public boolean isGroup() {
        return isgroup != 0;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public boolean isCreated() {
        return mCreated;
    }

    public void setCreated() {
        mCreated = true;
    }
}
