package com.scurab.android.anuitor.reflect;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;

import java.lang.reflect.Method;
import java.util.HashSet;

/**
 * @author jbruchanov
 * @since 2014-05-28 16:34
 */
@TargetApi(value = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class WindowManagerGlobalReflector extends Reflector<Object> implements WindowManager {

    public WindowManagerGlobalReflector() {
        super(getRealInstance());
    }

    public String[] getViewRootNames() {
        return callByReflection();
    }

    public View getRootView(String name) {
        return callByReflection(name);
    }

    private static Object getRealInstance() {
        try {
            Class<?> clz = Class.forName("android.view.WindowManagerGlobal");
            Method m = clz.getDeclaredMethod("getInstance");
            m.setAccessible(true);
            return m.invoke(null, null);
        } catch (Exception e) {
            throw new RuntimeException("Problem with calling android.view.WindowManagerGlobal#getInstance()", e);
        }
    }

    public Activity[] getActivites() {
        HashSet<Activity> result = new HashSet<Activity>();
        for (String s : getViewRootNames()) {
            View v = getRootView(s);
            if (v != null) {
                Context c = v.getContext();
                if (c instanceof Activity) {
                    result.add((Activity) c);
                }
            }
        }
        return result.toArray(new Activity[result.size()]);
    }

    @Override
    public Activity getCurrentActivity() {
        String[] viewRootNames = getViewRootNames();
        for (int i = viewRootNames.length - 1; i >= 0; i--) {
            String name = viewRootNames[i];
            View v = getRootView(name);
            Context c;
            if (v != null && (c = v.getContext()) instanceof Activity) {
                return (Activity) c;
            }
        }
        return null;
    }

    @Override
    public View getCurrentRootView() {
        String[] viewRootNames = getViewRootNames();
        for (int i = viewRootNames.length - 1; i >= 0; i--) {
            String name = viewRootNames[i];
            View v = getRootView(name);
            if (v != null) {
                return v;
            }
        }
        return null;
    }

    @Override
    public View getRootView(int index) {
        return WindowManagerProvider.getRootView(this, index);
    }
}