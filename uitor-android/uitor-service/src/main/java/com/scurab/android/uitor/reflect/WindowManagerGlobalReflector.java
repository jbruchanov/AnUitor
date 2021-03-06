package com.scurab.android.uitor.reflect;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;

import com.scurab.android.uitor.extract2.ExtractorExtMethodsKt;

import java.lang.reflect.Method;
import java.util.HashSet;

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
            return m.invoke(null, (Object[])null);
        } catch (Exception e) {
            throw new RuntimeException("Problem with calling android.view.WindowManagerGlobal#getInstance()", e);
        }
    }

    public Activity[] getActivities() {
        HashSet<Activity> result = new HashSet<>();
        for (String s : getViewRootNames()) {
            View v = getRootView(s);
            if (v != null) {
                Activity a = ExtractorExtMethodsKt.getActivity(v);
                if (a != null) {
                    result.add(a);
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
            if (v != null) {
                return ExtractorExtMethodsKt.getActivity(v);
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