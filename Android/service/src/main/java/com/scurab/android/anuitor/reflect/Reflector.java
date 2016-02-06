package com.scurab.android.anuitor.reflect;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.scurab.android.anuitor.tools.DOM2XmlPullBuilder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.xml.transform.TransformerException;

/**
 * Created by jbruchanov on 22/05/2014.
 */
public abstract class Reflector<T> {

    protected final T mReal;
    protected final Class<?> mClass;

    public Reflector(T real) {
        mReal = real;
        mClass = mReal.getClass();
    }

    protected <T> T callByReflection(Object... params) {
        return callMethodByReflection(null, params);
    }

    protected <T> T callMethodByReflection(@Nullable String methodName, @NonNull Object... params) {
        if (methodName == null) {
            methodName = getCalleeMethod();
        }
        Class<?>[] clzs = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            clzs[i] = params[i].getClass();
        }
        fixAutoboxing(clzs);

        Class<?> clz = mClass;
        while (clz != null) {
            try {
                Method m = clz.getDeclaredMethod(methodName, clzs);
                m.setAccessible(true);
                return (T) m.invoke(mReal, params);
            } catch (Exception e) {
                clz = clz.getSuperclass();
            }
        }
        throw new RuntimeException("Unable to find method: " + methodName + "(" + Arrays.toString(clzs) + ")");
    }

    private String getCalleeMethod() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[4].getMethodName();
        if ("callByReflection".equals(methodName)) {
            methodName = stackTrace[5].getMethodName();
        }
        return methodName;//getThreadStackTrace, getCalleeMethod, callByReflection, our method
    }

    protected <T> T getFieldValue(String fieldName) {
        Class<?> clz = mClass;
        while (clz != null) {
            try {
                Field f = clz.getDeclaredField(fieldName);
                f.setAccessible(true);
                return (T) f.get(mReal);
            } catch (Exception e) {
                clz = clz.getSuperclass();
            }
        }
        throw new RuntimeException("Unable to find field:" + fieldName);
    }

    //FIXME: naive, if there is nonPrimitive as param, it will fail
    protected void fixAutoboxing(Class<?>[] params) {
        for (int i = 0; params != null && i < params.length; i++) {
            params[i] = fixAutoboxing(params[i]);
        }
    }

    public static Class fixAutoboxing(Class<?> clz) {
        if (clz == Integer.class) {
            return int.class;
        } else if (clz == Boolean.class) {
            return boolean.class;
        } else if (clz == Short.class) {
            return short.class;
        } else if (clz == Character.class) {
            return char.class;
        } else if (clz == Byte.class) {
            return byte.class;
        } else if (clz == Long.class) {
            return long.class;
        } else if (clz == Double.class) {
            return double.class;
        } else if (clz == Float.class) {
            return float.class;
        }
        return clz;
    }

    protected String transformToString(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException, TransformerException {
        return DOM2XmlPullBuilder.transform(xmlPullParser);
    }
}
