package com.cnh.android.eagleongo.udw;

/**
 * Created by Hai on 4/21/17.
 */

public class WidgetInfo {
    public static final int NO_LIMIT = -1;

    public final String id;

    public final String widgetPackageName;
    public final String widgetClassName;
    public final int width;
    public final int height;
    public final int categories;
    public final int title;
    public final int description;
    public final boolean maximizable;
    public final boolean fullMaximizable;
    public final int order;
    public final int position;
    public final String initParams;
    public final String groupParams;
    public final String previewIcon;
    public final String helpPackageName;
    public final String helpClassName;

    public WidgetInfo(String id) {
        this(id, null, null, null, null, 0, 0, 0, 0, 0, 0, 0, null, false, false, null, null);
    }

    public WidgetInfo(String id, String widgetPackageName, String widgetClassName,
                      String initParams, String groupParams,
                      int categories, int position, int order,
                      int title, int description, int width, int height, String previewIcon,
                      boolean maximizable, boolean fullMaximizable,
                      String helpPackageName, String helpClassName) {

        this.id = id;
        this.widgetPackageName = widgetPackageName;
        this.widgetClassName = widgetClassName;
        this.categories = categories;
        this.title = title;
        this.description = description;
        this.width = width;
        this.height = height;
        this.maximizable = maximizable;
        this.fullMaximizable = fullMaximizable;
        this.order = order;
        this.position = position;
        this.initParams = initParams;
        this.groupParams = groupParams;
        this.previewIcon = previewIcon;
        this.helpPackageName = helpPackageName;
        this.helpClassName = helpClassName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((widgetClassName == null) ? 0 : widgetClassName.hashCode());
        result = prime * result + ((widgetPackageName == null) ? 0 : widgetPackageName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof WidgetInfo))
            return false;
        WidgetInfo other = (WidgetInfo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (widgetClassName == null) {
            if (other.widgetClassName != null)
                return false;
        } else if (!widgetClassName.equals(other.widgetClassName))
            return false;
        if (widgetPackageName == null) {
            if (other.widgetPackageName != null)
                return false;
        } else if (!widgetPackageName.equals(other.widgetPackageName))
            return false;
        return true;
    }

    public String toString() {
        return id != null ? id : "Unknown";
    }
}