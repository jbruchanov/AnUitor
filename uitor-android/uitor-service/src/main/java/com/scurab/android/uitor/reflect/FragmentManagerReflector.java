package com.scurab.android.uitor.reflect;

import android.app.Fragment;
import android.app.FragmentManager;
import android.util.SparseArray;

import com.scurab.android.uitor.tools.CollectionTools;

import java.util.List;

public class FragmentManagerReflector extends Reflector<FragmentManager> {
    public FragmentManagerReflector(FragmentManager real) {
        super(real);
    }

    @SuppressWarnings("unchecked")
    public List<Fragment> getFragments() {
        Object o = getFieldValue("mActive");
        if (o == null) {
            return null;
        } else if (o instanceof List) {
            return (List<Fragment>) o;
        } else if (o instanceof SparseArray) {
            SparseArray<Fragment> sparseArray = (SparseArray<Fragment>) o;
            return CollectionTools.toList(sparseArray);
        } else {
            throw new IllegalStateException(String.format("Invalid object type returned, it's '%s'", o.getClass().getName()));
        }
    }
}
