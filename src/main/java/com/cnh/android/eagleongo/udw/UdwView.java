package com.cnh.android.eagleongo.udw;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.cnh.android.eagleongo.R;
import dalvik.system.PathClassLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;

import static android.content.Context.CONTEXT_INCLUDE_CODE;

/**
 * Created by Hai on 4/22/17.
 */
public class UdwView extends GenericUdw<UdwView> {
    boolean mIsEditMode = false;
    boolean mIsMockup = false;

    Context mContext;

    public UdwView() {
        super();
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

    public View loadExternalUdw(Context context, GenericUdw<?> el)
            throws PackageManager.NameNotFoundException, ClassNotFoundException, IllegalArgumentException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException, NullPointerException {
        return loadExternalView(context, el.packageName, el.className);
    }

    /**
     * Load a UDW from external APK.
     */
    public View loadExternalView(Context context, String packageName, String className)
            throws PackageManager.NameNotFoundException, ClassNotFoundException, ClassCastException, IllegalArgumentException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException, NullPointerException {

        // Dynamic class loading from different apk
        Context apkContext = loadExternalContext(context, packageName);
        Class<?> cls = loadExternalClass(apkContext, packageName, className);

        // Clear inflater cache to avoid clashes between LayoutInflaters!
        clearInflaterCache(apkContext);

        // The View class is loaded by SystemClassLoader, so a cast to View is safe.
        return (View) cls.getConstructor(Context.class).newInstance(apkContext);
    }

    /**
     * Load the android Context from an external APK
     * @param packageName the package of the apk as defined in the manifest
     * @return the external context;
     * @throws PackageManager.NameNotFoundException if the package was not found
     * @throws NullPointerException if the context couldn't be loaded
     */
    public synchronized Context loadExternalContext(Context context, String packageName)
            throws PackageManager.NameNotFoundException, NullPointerException {
        return context.createPackageContext(packageName, CONTEXT_INCLUDE_CODE);
    }

    public Class<?> loadExternalClass(Context apkContext, String packageName, String className)
            throws PackageManager.NameNotFoundException, ClassNotFoundException {
        // Alternative: DexClassLoader, cfr. https://github.com/dalinaum/custom-class-loading-sample/blob/master/src/com/example/dex/MainActivity.java
        String apkName = apkContext.getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
        PathClassLoader pathClassLoader = new PathClassLoader(apkName, apkContext.getClassLoader());

        return Class.forName(className, true, pathClassLoader);
    }

    /**
     * The LayoutInflater caches the constructors of every view, this can cause problems if the views are
     * loaded via reflection. In case of exceptions loading views call this method to clear the cache.
     * Do not overuse as it can kill the performance.
     * @param context
     */
    public static void clearInflaterCache(Context context) {
        try {
            Field ctorMapField = LayoutInflater.class.getDeclaredField("sConstructorMap");
            ctorMapField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Constructor<? extends View>> ctorMap = (HashMap<String, Constructor<? extends View>>)
                    ctorMapField.get(LayoutInflater.from(context));
            Iterator<String> i = ctorMap.keySet().iterator();
            while (i.hasNext()) {
                String name = i.next();
                if (name.startsWith("com.cnh")) i.remove();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}
