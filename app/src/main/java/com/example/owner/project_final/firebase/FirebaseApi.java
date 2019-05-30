package com.example.owner.project_final.firebase;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.owner.project_final.L;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseApi {

    public static void accessToLogin(String id, String password, final OnResultListener listener) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(id, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                L.e(":::::: [Sing In Complete] : " + task.isSuccessful());
                if (task.isSuccessful()) {
                    listener.onComplete(task);
                } else {
                    L.e(":::::::::::::: Sing In Failed");
                    listener.onComplete(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure(e);
            }
        });
    }

    public static void createToUserID(String id, String password, final OnResultListener listener) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(id, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                L.e(":::::: [Create UserModel Complete] : " + task.isSuccessful());
                if (task.isSuccessful()) {
                    listener.onComplete(task);
                } else {
                    L.e(":::::::::::::: Create UserModel Failed");
                    listener.onComplete(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure(e);
            }
        });

    }

    public static void sendFirebaseStorage(String photoid, Uri uri, final int index, final int resultPage, final OnPhotoResultListener listener) {

        FirebaseStorage storage = FirebaseStorage.getInstance();

        String childFolder = null;

        childFolder = "roomImage" + (index + 1) + ".jpg";

        StorageReference storageRef = storage.getReferenceFromUrl(PublicVariable.FIREBASE_STORAGE).child(PublicVariable.FIREBASE_STORAGE_ROOMS).child(FirebaseApi.getCurrentUser().getUid()).child(childFolder).child(photoid);
        storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if ((resultPage - 1) == index) {
                    L.e(":::::::::::uplaod onSuccess : " + index);
                    listener.onComplete();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                L.e(":::::::::::uplaod onFailure");
                listener.onFailure(e);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                L.e(":::::::::::uplaod onProgress : " + progress);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public static FirebaseUser getCurrentUser() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return null;
        }
        return FirebaseAuth.getInstance().getCurrentUser();
    }


}
