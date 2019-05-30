package com.example.owner.project_final.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface OnResultListener {
    void onComplete(Task<AuthResult> task);

    void onFailure(Exception e);
}
