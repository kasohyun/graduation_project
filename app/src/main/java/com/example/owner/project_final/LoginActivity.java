package com.example.owner.project_final;

/**
 * ViewPager - Fragment
 * TabHost (Failed)
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.project_final.firebase.FirebaseApi;
import com.example.owner.project_final.firebase.MessageUtils;
import com.example.owner.project_final.firebase.OnResultListener;
import com.example.owner.project_final.model.PreferenceHelper;
import com.example.owner.project_final.model.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    //[오투잡] 2019.04.03 로그인 로직 변경



    @BindView(R.id.loginActivity_edittext_id)
    EditText id;

    @BindView(R.id.loginActivity_edittext_password)
    EditText password;

    private ProgressDialog mProgressDialog;

    private static final int RC_SIGN_IN = 900;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth fireauth;
    private SignInButton buttonGoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


//-------------

        fireauth = FirebaseAuth.getInstance();
        buttonGoogle = findViewById(R.id.btn_googleSignIn);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
//-------------
        processStart();

    }

//------------



    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 구글로그인 버튼 응답
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // 구글 로그인 성공
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        fireauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                            // Log.d(TAG, "signInWithCredential:success");
                        } else {
                            // 로그인 실패
                        }

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fireauth.getCurrentUser();
        fireauth.getInstance().signOut();
    }
    //------------
    private void processStart() {
        if (FirebaseApi.getCurrentUser() != null) {
            L.e("::::최신 로그인 기록이 있음");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
            return;
        }

        L.e("::::로그인 기록없으므로 로그인 로직 진행");
    }


   /*
    @Override
    protected void onStart() {
        super.onStart();

    }
    */

    @OnClick(R.id.loginActivity_button_login)
    public void login(View view) {
        hideKeyboard();
        if (TextUtils.isEmpty(id.getText().toString()) || id.getText().toString().equalsIgnoreCase("")) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "아이디를 입력해 주세요.");
            return;
        }

        if (TextUtils.isEmpty(password.getText().toString()) || password.getText().toString().equalsIgnoreCase("")) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "비밀번호를 입력해 주세요.");
            return;
        }

        showProgressDialog("로그인 중입니다. 잠시만 기다려주세요.");
        FirebaseApi.accessToLogin(id.getText().toString(), password.getText().toString(), new OnResultListener() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task != null) {
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseApi.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                UserModel user = dataSnapshot.getValue(UserModel.class);
                                if (user != null) {
                                    L.e("::::로그인 성공한 유저 : " + user.toString());
                                    //[오투잡] 닉네임을 db 에 저장 한다 매번 닉네임 하나떄문에 User 테이블을 호출할필요가 없음. 로그인 시점에 한번만 저장하고 사용함
                                    PreferenceHelper.setNickName(getApplicationContext(),user.getName());
                                    onSucces();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Exception e) {
                L.e("::::: e : " + e);
//                    ToastUtils.show(,e.getMessage());
                String message = null;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    message = "Passwords do not match.";
                } else if (e instanceof FirebaseAuthInvalidUserException) {
                    message = "The ID does not exist.";
                } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    message = "Please follow the email address form.";
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    message = "You can not log in due to too many attempts.";
                }

                final String finalMessage = message;
                onFail(finalMessage);

            }
        });

    }

    public void onSucces() {
        hideProgressDialog();
        showSnackBar("로그인에 성공 하였습니다.");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        }, 1200);
    }

    private void onFail(String finalMessage) {
        showSnackBar(finalMessage);
        hideProgressDialog();
    }

    @OnClick(R.id.loginActivity_button_signup)
    public void signup(View view) {
        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerIntent);
    }


    public void showSnackBar(String Text) {
        Snackbar.make(findViewById(android.R.id.content), Text, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();
    }


    public void showProgressDialog(String title) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(title);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        mProgressDialog.show();
    }


    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private void hideKeyboard() {
        //hide keyboard
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            String serviceName = Context.INPUT_METHOD_SERVICE;
            InputMethodManager imm = (InputMethodManager) getSystemService(serviceName);
            int stateHide = InputMethodManager.HIDE_NOT_ALWAYS;
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), stateHide);
        }
    }

}