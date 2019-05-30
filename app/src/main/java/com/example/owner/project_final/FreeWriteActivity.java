package com.example.owner.project_final;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.owner.project_final.firebase.FirebaseApi;
import com.example.owner.project_final.firebase.ImageLoaderHelper;
import com.example.owner.project_final.firebase.OnPhotoResultListener;
import com.example.owner.project_final.firebase.PublicVariable;
import com.example.owner.project_final.location.LocationProvider;
import com.example.owner.project_final.model.PreferenceHelper;
import com.example.owner.project_final.model.FreeWrite;
import com.example.owner.project_final.volley.VolleyResult;
import com.example.owner.project_final.volley.VolleyService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FreeWriteActivity extends AppCompatActivity {
    //[오투잡] 2019.04.12 글쓰기 부분 진입 부분 버터나이프 기능적용
    //[오투잡] 2019.04.13 글쓰기 부분 데이터 및 구글맵 셋팅 Firebase 서버로 데이터 add 완료
    //조언  public static Activity roomWriteActivity;  방식으로 Static 을 너무 쓰면 리로스를 많이잡아먹습니다 가급적 satic은 자제해주세요.
    Intent intent;

    @BindView(R.id.title_date)
    TextView date;

    @BindView(R.id.title)
    EditText editTitle;

    @BindView(R.id.User)
    EditText editUser;

    @BindView(R.id.Contents)
    EditText editDiscription;
/* 사진 삽입 */
        @BindView(R.id.photo_1)
        ImageView Photo1;
        @BindView(R.id.default_photo_1)
        ImageView Photodefalut1;
        @BindView(R.id.photo_2)
        ImageView Photo2;
        @BindView(R.id.default_photo_2)
        ImageView Photodefalut2;
/* 사진 삽입 */
    @BindView(R.id.scroll)
    ScrollView scrollView;

    CheckBox unknown_name_writer;

    public final static int REQUEST_PICK_IMAGE = 0x20;

    private String postingDate;
    private String[] photoUri = new String[]{"", ""};

    public int number = 1;
    public ProgressDialog mProgressDialog;

    private boolean unknown_name_writer_check;
    public int mPhotoPosition = -1;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    // For Toolbar ---------------------------------------------------------------------------------
    Toolbar toolBar;
    //----------------------------------------------------------------------------------------------

    // For Navigation Drawer -----------------------------------------------------------------------
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        L.e("::::FreeWriteActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_write);
        ButterKnife.bind(this);

        //최신 날짜 셋팅
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        postingDate = simpleDateFormat.format(System.currentTimeMillis());
        date.setText(postingDate);
        editUser.setText(PreferenceHelper.getNickName(getApplicationContext()));

        // For Toolbar -----------------------------------------------------------------------------
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("자유 게시판 글쓰기");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_black_24dp);
        // -----------------------------------------------------------------------------------------

        // For Navigation Drawer -------------------------------------------------------------------
        drawerLayout = (DrawerLayout)findViewById(R.id.activity);  //각 레이아웃의 가장 큰 DrawerLayout 이름
        navigationView = (NavigationView)findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                int id = item. getItemId();

                switch (id) {
                    case R.id.navi_tab1:    //오늘 하루
                        //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( getApplicationContext(),Tab1Activity.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab2:    //위치 서비스
                        //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( getApplicationContext(),Tab2Activity.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3:    //게시판
                        //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( getApplicationContext(),Tab3Activity.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_1:    //공동구매 게시판
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( getApplicationContext(), PurchaseActivity.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_2:    //단기방대여 게시판
                        //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( getApplicationContext(), RoomActivity.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_3:    //음식주문 게시판
                        ////Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( getApplicationContext(), FoodActivity.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_4:    //취미여가 게시판
                        //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( getApplicationContext(), HobbyActivity.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_5:    //자유게시판
                        //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( getApplicationContext(), FreeActivity.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab4:    //무드등
                        //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( getApplicationContext(), BluetoothLED.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab5:    //미니게임
                        //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( getApplicationContext(), GameActivity.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        break;
                    case R.id.navi_tab6:    //마이페이지
                        //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( getApplicationContext(), MypageActivity.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        break;
                }

                return true;
            }
        });
        // -----------------------------------------------------------------------------------------
    }


    @OnClick(R.id.saveButton)
    public void save(View view) {
        if (!validateForm()) {
            return;
        }

        unknown_name_writer = (CheckBox) findViewById(R.id.unknown_name_writer);
        if (unknown_name_writer.isChecked()) {
            unknown_name_writer_check = true;
        }
/* 사진 삽입 */
        final ArrayList<Uri> storePhotoArray = new ArrayList<>();
        int i = 0;
        while (i < photoUri.length) {
            if (!TextUtils.isEmpty(photoUri[i]) && !photoUri[i].equalsIgnoreCase("")) {
                storePhotoArray.add(Uri.parse(photoUri[i]));
            }
            i++;
        }
        /* 사진 삽입 */
        showProgressDialog("업로드 중입니다.");
        String autoKey = FirebaseDatabase.getInstance().getReference().child(PublicVariable.FIREBASE_CHILD_FREES).push().getKey();
        final String storageKey = FirebaseDatabase.getInstance().getReference().child(PublicVariable.FIREBASE_CHILD_FREES).push().getKey();
/*
        FreeWrite freeWrite = new FreeWrite(FirebaseApi.getCurrentUser().getUid(), editTitle.getText().toString(), editUser.getText().toString(),
                unknown_name_writer_check, storageKey, editDiscription.getText().toString(), postingDate, autoKey);
*/
        FreeWrite freeWrite = new FreeWrite(FirebaseApi.getCurrentUser().getUid(), editTitle.getText().toString(), editUser.getText().toString(),
                unknown_name_writer_check, editDiscription.getText().toString(), postingDate, autoKey);
/* 사진 삽입 */
        L.e("::::::auto key : " + autoKey);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(PublicVariable.FIREBASE_CHILD_FREES).child(FirebaseApi.getCurrentUser().getUid()).child(autoKey).setValue(freeWrite).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    for (int i = 0; i < storePhotoArray.size(); i++) {
                        FirebaseApi.sendFirebaseStorage(storageKey, storePhotoArray.get(i), i, storePhotoArray.size(), new OnPhotoResultListener() {
                            @Override
                            public void onComplete() {
                                hideProgressDialog();
                                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
                                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                                new AlertDialog.Builder(FreeWriteActivity.this, R.style.Theme_AppCompat_Light_Dialog)
                                        .setTitle("알림")
                                        .setMessage("업로드가 완료 되었습니다. ")
                                        .setCancelable(false)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(getApplicationContext(),  "onClick", Toast.LENGTH_LONG).show();
                                                intent = new Intent().setClass( getApplicationContext(),FreeActivity.class );
                                                finish();
                                            }
                                        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        Toast.makeText(getApplicationContext(),  "onDismiss", Toast.LENGTH_LONG).show();
                                    }
                                }).show().getWindow().setLayout(width, height);
                            }
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(getApplicationContext(),  "onFailure", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
/* 사진 삽입 */
    }

    @OnClick(R.id.cancelButton)
    public void cancle(View view) {
        intent = new Intent().setClass( getApplicationContext(), FreeActivity.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public String getResultDay(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(calendar.getTimeInMillis());

    }


    public void showProgressDialog(String title) {
        L.e(":::title : " + title);
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

    public void showSnakbar(String text, int priod) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), text, priod);
            snackbar.show();
        } else {
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        }
    }


    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(editTitle.getText().toString())) {
            editTitle.setError("Required");
            result = false;
        } else {
            editTitle.setError(null);
        }

        if (TextUtils.isEmpty(editUser.getText().toString())) {
            editUser.setError("Required");
            result = false;
        } else {
            editUser.setError(null);
        }

        if (TextUtils.isEmpty(editDiscription.getText().toString())) {
            editDiscription.setError("Required");
            result = false;
        } else {
            editDiscription.setError(null);
        }


        return result;
    }

/* 사진 삽입 */
        @OnClick(R.id.photo_view_1)
        public void setPhotoClick_01(View view) {
            Dexter.withActivity(FreeWriteActivity.this).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        mPhotoPosition = 0;
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, REQUEST_PICK_IMAGE);
                    }
                }
                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        }
        @OnClick(R.id.photo_view_2)
        public void setPhotoClick_02(View view) {
            Dexter.withActivity(FreeWriteActivity.this).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        mPhotoPosition = 1;
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, REQUEST_PICK_IMAGE);
                    }
                }
                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                    //[오투잡]이미지를 갤러리에서 Callback Url 을 콜백하는 부분;
                    L.i(":::::: OPEN_IMAGE_REQUEST_CODE");
                    try {
                        Uri uri;
                        if (data == null) {
                            return;
                        }
                        uri = data.getData();
                        L.e("::::uri : " + uri);
                        photoUri[mPhotoPosition] = uri.toString();
                        if (mPhotoPosition == 0) {
                            Photo1.setVisibility(View.VISIBLE);
                            Photodefalut1.setVisibility(View.GONE);
                            ImageLoaderHelper.setProfileImage(getApplicationContext(), uri, Photo1, "");
                        } else {
                            Photo2.setVisibility(View.VISIBLE);
                            Photodefalut2.setVisibility(View.GONE);
                            ImageLoaderHelper.setProfileImage(getApplicationContext(), uri, Photo2, "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
/* 사진 삽입 */
    // For Toolbar ---------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_general, menu);    //게시판 목록 외에서 사용할 툴바 메뉴
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity);

        switch (item.getItemId()) {
            case R.id.MainButton:
                intent = new Intent().setClass( getApplicationContext(), MainActivity.class );  //MainActivity로 이동
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.ChatButton:
                intent = new Intent().setClass( getApplicationContext(), ChattingActivity.class );  //ChattingActivity로 이동
                startActivity(intent);
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.MyPageButton:
                intent = new Intent().setClass( getApplicationContext(), MypageActivity.class );    //MyㅔageActivity로 이동
                startActivity(intent);
                return true;
            case R.id.LogOutButton:
                if(user != null){
                    FirebaseAuth.getInstance().signOut();
                    intent = new Intent().setClass( getApplicationContext(), LoginActivity.class ); //로그아웃 후 LoginActivity로 이동
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "로그아웃 성공", Toast.LENGTH_LONG).show();
                    overridePendingTransition(0, 0);
                }else{
                    Toast.makeText(getApplicationContext(), "로그아웃 실패", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
    //----------------------------------------------------------------------------------------------
}