package com.systemcorp.sdsu.schedule.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by soso on 8/5/17.
 */

public class NotificationInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInsIDservice";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRefreshedToken();
    }

    private void sendRefreshedToken() {

    }
}
