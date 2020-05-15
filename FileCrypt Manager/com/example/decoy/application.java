package com.example.decoy;

import android.app.Application;

import java.util.LinkedHashMap;

public class application extends Application {
    private static application mContext;
    LinkedHashMap<String, Object> objectLinkedHashMap = new LinkedHashMap<>();

    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static application getContext() {
        return mContext;
    }

    public Object getObject(String str) {
        return this.objectLinkedHashMap.get(str);
    }

    public void setObjects(String str, Object obj) {
        this.objectLinkedHashMap.put(str, obj);
    }
}
