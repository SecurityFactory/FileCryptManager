package com.example.decoy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class valueLoaderService extends Service {
    DebugStateChecker threadFetcher = new DebugStateChecker(this);

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            this.threadFetcher.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}