package com.scurab.android.uitor.reflect;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityThreadReflector extends Reflector<Object> {

    public ActivityThreadReflector() {
        super(getInstance());
    }

    @NonNull
    private static Object getInstance() {
        try {
            Class clz = Class.forName("android.app.ActivityThread");
            return callMethodByReflection(clz, null, "currentActivityThread");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    @NonNull
    public Application getApplication() {
        return getFieldValue(mReal, "mInitialApplication");
    }

    @NonNull
    public List<Activity> getActivities() {
        final ArrayList<Activity> result = new ArrayList<>();
        for (Object mActivityRecord : ((Map<?, Object>) getFieldValue(mReal, "mActivities")).values()) {
            result.add(getFieldValue(mActivityRecord, "activity"));
        }
        return result;
    }
}