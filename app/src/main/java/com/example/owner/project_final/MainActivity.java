package com.example.owner.project_final;

/*
다른 자바 파일에 툴바와 내비게이션 바와 관련된 것을 넣으면 앱 강제종료 당함
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.relex.circleindicator.CircleIndicator;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    FragmentPagerAdapter adapterViewPager;


    Intent intent;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vpPger =(ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAapter(getSupportFragmentManager());
        vpPger.setAdapter(adapterViewPager);

        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(vpPger);

        MobileAds.initialize(this, "ca-app-pub-1148515500898185~3187151648");


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("0228183F9190C9087CB63807C894022F")
                .build();
        mAdView.loadAd(adRequest);

        /*/ For AdView ------------------------------------------------------------------------------
        AdView mAdView = (AdView) findViewById(R.id.ads_main);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // -----------------------------------------------------------------------------------------*/

        // For Toolbar -----------------------------------------------------------------------------
        toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("우리동네 자취생");
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

        Button btn_first=(Button)findViewById(R.id.btn_first);  //tab1
        Button btn_second=(Button)findViewById(R.id.btn_second);    //tab2
        Button btn_third=(Button)findViewById(R.id.btn_third);  //tab3
        Button btn_fourth=(Button)findViewById(R.id.btn_fourth);    //LED
        Button btn_fifth=(Button)findViewById(R.id.btn_fifth);  //Game
        Button btn_sixth=(Button)findViewById(R.id.btn_sixth);  //FreeActivity

        btn_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( getApplicationContext(),Tab1Activity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        btn_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( getApplicationContext(),Tab2Activity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        btn_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( getApplicationContext(),Tab3Activity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        btn_fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( getApplicationContext(),BluetoothLED.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        btn_fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( getApplicationContext(),GameActivity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        btn_sixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( getApplicationContext(), FreeActivity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }

    public static class MyPagerAapter extends FragmentPagerAdapter{
        private static int NUM_ITEMS = 3;

        public MyPagerAapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }
        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    return FirstFragment.newInstance(0, "Page # 1");
                case 1:
                    return SecondFragment.newInstance(1, "Page # 2");
                case 2:
                    return ThirdFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }

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
                intent = new Intent().setClass( getApplicationContext(), MypageActivity.class );    //MypageActivity로 이동
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
                //Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
    //----------------------------------------------------------------------------------------------



}