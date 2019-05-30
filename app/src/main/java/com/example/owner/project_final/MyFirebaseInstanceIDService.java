package com.example.owner.project_final;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    // 메세지를 받기 위한 작업
    private static final String TAG="MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {


        String refreshedTocken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Refreshed token:" + refreshedTocken);

        sendRegistrationToServer(refreshedTocken);
    }

    private void sendRegistrationToServer(String token){

        // 기타작업
    }

}
