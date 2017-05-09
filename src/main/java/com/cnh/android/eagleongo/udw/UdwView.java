package com.cnh.android.eagleongo.udw;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.cnh.android.eagleongo.R;
import com.cnh.android.windowmanager.util.WmUtils;

import dalvik.system.PathClassLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;

import static android.content.Context.CONTEXT_IGNORE_SECURITY;
import static android.content.Context.CONTEXT_INCLUDE_CODE;

/**
 * Created by Hai on 4/22/17.
 */
public class UdwView extends GenericUdw<UdwView> {
    boolean mIsEditMode = false;
    boolean mIsMockup = false;

    public UdwView(String id, String packageName, String className) {
        super(id, packageName, className);
    }

    public UdwView(GenericUdw<?> el, boolean isEditMode) {
        super(el);

        mIsEditMode = isEditMode;
        mIsMockup = el instanceof MockupUdw;
    }

    @Override
    public View getUdwView(Context context) {
        // Cached view
        if (mUdwView != null) return mUdwView;

        // Inflate UDW stub
        try {
            // Dynamic class loading from different apk
            mUdwView = loadExternalUdw(context, this);
        }
        catch (Exception e) {
            e.printStackTrace();
            // Error view
            mUdwView = new MockupUdw(this, new Exception("Udw not found"), false).getView(context);
        }

        return mUdwView;
    }

    // Inflater view and add UDW
    @Override
    public View getView(Context context) {
        // Cached view
        if (mView != null) return mView;

        // Inflate layout
        mView = LayoutInflater.from(context).inflate(R.layout.view_display_udw, null);

        // Place real UDW
        View udwStub = mView.findViewById(R.id.udwStub);
        if (udwStub != null) {
            ((FrameLayout) udwStub).addView(getUdwView(context));
        }

        return mView;
    }

    private WmUtils mWmUtils;
    public View loadExternalUdw(Context context, GenericUdw<?> el)
            throws PackageManager.NameNotFoundException, ClassNotFoundException, IllegalArgumentException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException, NullPointerException {
        if (mWmUtils == null) mWmUtils = WmUtils.getInstance();

        // The loaded element extends a ViewGroup and implements UDW, but:
        // the UDW class here and in the external apk are considered *different*
        // because they belong to different class loaders. Thus, we cannot cast
        // to UDW here. Nevertheless, the View class is loaded by SystemClassLoader
        // so a cast to View is safe.
        return mWmUtils.loadExternalView(el.packageName, el.className);
    }
}
