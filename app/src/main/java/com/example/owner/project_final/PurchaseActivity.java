package com.example.owner.project_final;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.owner.project_final.adapter.PurchaseAdapter;
import com.example.owner.project_final.firebase.FirebaseApi;
import com.example.owner.project_final.firebase.PublicVariable;
import com.example.owner.project_final.location.LocationProvider;
import com.example.owner.project_final.model.PurchaseWrite;
import com.example.owner.project_final.volley.VolleyResult;
import com.example.owner.project_final.volley.VolleyService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PurchaseActivity extends AppCompatActivity {

  Intent intent;

  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

  // For Activity finish -------------------------------------------------------------------------
  public static Activity purchaseActivity;
  //----------------------------------------------------------------------------------------------

  // For Toolbar ---------------------------------------------------------------------------------
  Toolbar toolBar;
  //----------------------------------------------------------------------------------------------

  // For Navigation Drawer -----------------------------------------------------------------------
  NavigationView navigationView;
  DrawerLayout drawerLayout;
  // ---------------------------------------------------------------------------------------------

  // For ListView --------------------------------------------------------------------------------
  static ArrayList<String> items;
  static ArrayAdapter adapter;
  static ListView listView;
  static int count, checked;
  // ---------------------------------------------------------------------------------------------


  //[오투잡] mRecyclerView;
  @BindView(R.id.list)
  RecyclerView mRecyclerView;

  private PurchaseAdapter mPurchaseAdapter;

  private void onLoad() {
    //[오투잡] Firebase 서버에서 게시판 목록 데이터 요청
    final ArrayList<String> UserKeyList = new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    Query req = reference.child(PublicVariable.FIREBASE_CHILD_PURCHASES);


    req.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          UserKeyList.clear();
          Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
          Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
          while ((iterator.hasNext())) {
            final String key = iterator.next().getKey();
            UserKeyList.add(key);
          }

          for (String userKey : UserKeyList) {
            DatabaseReference qurey = FirebaseDatabase.getInstance().getReference().child(PublicVariable.FIREBASE_CHILD_PURCHASES).child(userKey);
            qurey.addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                  for (DataSnapshot child : dataSnapshot.getChildren()) {
                    PurchaseWrite item = child.getValue(PurchaseWrite.class);
                    mPurchaseAdapter.insertData(item);
                  }
                }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
            });
          }
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_purchase);
    ButterKnife.bind(this);

    // For Activity finish ---------------------------------------------------------------------
    purchaseActivity = PurchaseActivity.this;
    // -----------------------------------------------------------------------------------------

    // For Toolbar -----------------------------------------------------------------------------
    toolBar = (Toolbar)findViewById(R.id.toolbar);
    setSupportActionBar(toolBar);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setTitle("공동구매 게시판");
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

    // For ListView ----------------------------------------------------------------------------
    // 빈 데이터 리스트 생성.
    items = new ArrayList<String>();
    // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
    adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items);

    // listview 생성 및 adapter 지정.
    listView = (ListView) findViewById(R.id.listView);    //해당 리스트뷰 이름
    listView.setAdapter(adapter);

    count = adapter.getCount();
    checked = listView.getCheckedItemPosition();

    mPurchaseAdapter = new PurchaseAdapter(getApplicationContext()) {
      @Override
      public void selectItem(PurchaseWrite item) {
        L.e(":::::Click item : " + item.toString());
        Intent intent = new Intent(PurchaseActivity.this, PurchaseDetailActivity.class);
        intent.putExtra("PurchaseWrite", item);
        startActivity(intent);
      }
    };
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    mRecyclerView.setAdapter(mPurchaseAdapter);
    onLoad();
  }

  // For Toolbar ---------------------------------------------------------------------------------
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    //return super.onCreateOptionsMenu(menu);
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.post_menu, menu);    //각자에 맞는 R.menu. 파일 작성할 것
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    drawerLayout = (DrawerLayout)findViewById(R.id.activity);  //각자에 맞는 레이아웃의 가장 겉 DrawerLayout 이용할 것

    switch (item.getItemId()) {
      case R.id.MainButton_post:
        intent = new Intent().setClass( getApplicationContext(), MainActivity.class );
        startActivity(intent);
        return true;
      case R.id.ChatButton_post:
        intent = new Intent().setClass( getApplicationContext(),ChattingActivity.class );
        startActivity(intent);
        overridePendingTransition(0, 0);
        return true;
      case android.R.id.home:
        drawerLayout.openDrawer(GravityCompat.START);
        return true;
      case R.id.action_write_post:
        //[오투잡] 2019.04.12 글쓰기 버튼 클릭시 수정
        if (!gpsEnabled()) {
          int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
          int height = WindowManager.LayoutParams.WRAP_CONTENT;
          new AlertDialog.Builder(PurchaseActivity.this, R.style.Theme_AppCompat_Light_Dialog)
                  .setMessage("계속하려면 Google 위치 서비스를 \n사용하는 기기 위치 기능을 사용 설정하세요")
                  .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), PublicVariable.GPS_REQUEST_CODE);
                    }
                  }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
          }).show().getWindow().setLayout(width, height);
        } else {
          Dexter.withActivity(PurchaseActivity.this).withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
              if (report.areAllPermissionsGranted()) {
                intent = new Intent().setClass(PurchaseActivity.this, PurchaseWriteActivity.class);
                startActivity(intent);
                purchaseActivity.finish();
                overridePendingTransition(0, 0);
              }
            }
            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
              token.continuePermissionRequest();
            }
          }).check();
        }
        return true;
      case R.id.MyPageButton_post:
        Toast.makeText(getApplicationContext(), "마이페이지 버튼 클릭됨", Toast.LENGTH_LONG).show();
        startActivity(intent);
        return true;
      case R.id.LogOutButton_post:
        if(user != null){
          Toast.makeText(getApplicationContext(), "로그아웃 버튼 클릭됨", Toast.LENGTH_LONG).show();
          FirebaseAuth.getInstance().signOut();
        }else{
          Toast.makeText(getApplicationContext(), "로그아웃실패", Toast.LENGTH_LONG).show();
        }
        intent = new Intent().setClass( getApplicationContext(), LoginActivity.class );
        startActivity(intent);
        overridePendingTransition(0, 0);
        return true;
      default:
        Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }
  }

  public boolean gpsEnabled() {
    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
  }
  //----------------------------------------------------------------------------------------------
}