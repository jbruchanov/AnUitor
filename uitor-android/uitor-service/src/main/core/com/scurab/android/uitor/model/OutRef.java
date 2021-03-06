package com.scurab.android.uitor.model;

public class OutRef<T> {

    private T mValue;

    public OutRef() {
    }

    public OutRef(T value) {
        this.mValue = value;
    }

    public T getValue() {
        return mValue;
    }

    public void setValue(T value) {
        mValue = value;
    }
}
