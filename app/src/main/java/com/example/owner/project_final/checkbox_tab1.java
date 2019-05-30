package com.example.owner.project_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class checkbox_tab1 extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail(); // 접근자

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference tab1Ref = db.collection(email); // 회원들의 전체정보 collection, 그 중 하나정보 document


    CheckBox ch1,ch2,ch3,ch4,ch5,ch6,ch7,ch8,ch9,ch10,
            ch11,ch12,ch13,ch14,ch15,ch16,ch17,ch18,ch19,ch20,ch21,ch22,ch23,ch24;
    TextView tv ;
    Button btn1, btn2;

    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1dialog);

        Intent intent = getIntent();
       final String data = intent.getExtras().getString("today");
       // tab1Activity에서 가져온 날짜데이터




        ch1 = (CheckBox)findViewById(R.id.chkhappy);
        ch2 =(CheckBox)findViewById(R.id.chksad);
        ch3 = (CheckBox)findViewById(R.id.chkanger);
        ch4 = (CheckBox)findViewById(R.id.chkjoy);
        ch5= (CheckBox)findViewById(R.id.chklove);
        ch6 =(CheckBox)findViewById(R.id.chkhate);
        ch7 =(CheckBox)findViewById(R.id.chkgreed);
        ch8=(CheckBox)findViewById(R.id.chkgloom);
        ch9=(CheckBox)findViewById(R.id.chkhousefood);
        ch10=(CheckBox)findViewById(R.id.chkoutfood);
        ch11=(CheckBox)findViewById(R.id.chkdelivery) ;
        ch12 =(CheckBox)findViewById(R.id.chkFetc);
        ch13 = (CheckBox) findViewById(R.id.chkmovie);
        ch14=(CheckBox)findViewById(R.id.chkbook);
        ch15=(CheckBox)findViewById(R.id.chkmuseum);
        ch16=(CheckBox)findViewById(R.id.chkhobby);
        ch17 = (CheckBox)findViewById(R.id.chkdrink);
        ch18=(CheckBox)findViewById(R.id.chkshopping);
        ch19 = (CheckBox)findViewById(R.id.chknot);
        ch20=(CheckBox)findViewById(R.id.chkMetc);
        ch21 = (CheckBox)findViewById(R.id.chkrest);
        ch22 = (CheckBox)findViewById(R.id.chkhospital);
        ch23 = (CheckBox) findViewById(R.id.chkbeauty);
        ch24 = (CheckBox)findViewById(R.id.chkexercise);
        tv=(TextView)findViewById(R.id.eddiary);



        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);

       //  fd = FirebaseDatabase.getInstance();
        // fdRef = fd.getReference("activity_tab1");


        DocumentReference Doctab1 = db.collection(email).document(data); // 캘린더에서 선택날 날짜에 대한 정보들을 조회!

        Doctab1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {



                        System.out.println("성공"+document.getData());



                        Boolean b1 = document.getBoolean("ch1");
                        Boolean b2 = document.getBoolean("ch2");
                        Boolean b3 = document.getBoolean("ch3");
                        Boolean b4 = document.getBoolean("ch4");
                        Boolean b5 = document.getBoolean("ch5");
                        Boolean b6 = document.getBoolean("ch6");
                        Boolean b7 = document.getBoolean("ch7");
                        Boolean b8 = document.getBoolean("ch8");
                        Boolean b9 = document.getBoolean("ch9");
                        Boolean b10 = document.getBoolean("ch10");
                        Boolean b11 = document.getBoolean("ch11");
                        Boolean b12 = document.getBoolean("ch12");
                        Boolean b13 = document.getBoolean("ch13");
                        Boolean b14 = document.getBoolean("ch14");
                        Boolean b15 = document.getBoolean("ch15");
                        Boolean b16 = document.getBoolean("ch16");
                        Boolean b17 = document.getBoolean("ch17");
                        Boolean b18 = document.getBoolean("ch18");
                        Boolean b19 = document.getBoolean("ch19");
                        Boolean b20 = document.getBoolean("ch20");
                        Boolean b21 = document.getBoolean("ch21");
                        Boolean b22 = document.getBoolean("ch22");
                        Boolean b23 = document.getBoolean("ch23");
                        Boolean b24 = document.getBoolean("ch24");

                        String text = document.getString("text");


                        ch1.setChecked(b1);
                        ch2.setChecked(b2);
                        ch3.setChecked(b3);
                        ch4.setChecked(b4);
                        ch5.setChecked(b5);
                        ch6.setChecked(b6);
                        ch7.setChecked(b7);
                        ch8.setChecked(b8);
                        ch9.setChecked(b9);
                        ch10.setChecked(b10);
                        ch11.setChecked(b11);
                        ch12.setChecked(b12);
                        ch13.setChecked(b13);
                        ch14.setChecked(b14);
                        ch15.setChecked(b15);
                        ch16.setChecked(b16);
                        ch17.setChecked(b17);
                        ch18.setChecked(b18);
                        ch19.setChecked(b19);
                        ch20.setChecked(b20);
                        ch21.setChecked(b21);
                        ch22.setChecked(b22);
                        ch23.setChecked(b23);
                        ch24.setChecked(b24);

                        tv.setText(text);







                    } else {
                        System.out.println("실패");
                    }


                }else{

                    System.out.println("실패222");
                }

            }
        });



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //    String key =  fdRef.push().getKey();

                Boolean b1 =   ch1.isChecked();
                Boolean b2 =   ch2.isChecked();
                Boolean b3 =   ch3.isChecked();
                Boolean b4 =   ch4.isChecked();
                Boolean b5 =   ch5.isChecked();
                Boolean b6 =   ch6.isChecked();
                Boolean b7 =   ch7.isChecked();
                Boolean b8 =   ch8.isChecked();
                Boolean b9 =   ch9.isChecked();
                Boolean b10 =  ch10.isChecked();
                Boolean b11 =   ch11.isChecked();
                Boolean b12 =   ch12.isChecked();
                Boolean b13 =   ch13.isChecked();
                Boolean b14 =   ch14.isChecked();
                Boolean b15 =   ch15.isChecked();
                Boolean b16 =   ch16.isChecked();
                Boolean b17 =   ch17.isChecked();
                Boolean b18 =   ch18.isChecked();
                Boolean b19 =   ch19.isChecked();
                Boolean b20 =  ch20.isChecked();
                Boolean b21 =   ch21.isChecked();
                Boolean b22 =   ch22.isChecked();
                Boolean b23 =   ch23.isChecked();
                Boolean b24 =   ch24.isChecked();


                String text = tv.getText().toString();

                Map<String, Object > postValues = new HashMap<>();

                postValues.put("today", data);
                postValues.put("ch1", b1);
                postValues.put("ch2", b2);
                postValues.put("ch3", b3);
                postValues.put("ch4", b4);
                postValues.put("ch5", b5);
                postValues.put("ch6", b6);
                postValues.put("ch7", b7);
                postValues.put("ch8", b8);
                postValues.put("ch9", b9);

                postValues.put("ch10", b10);
                postValues.put("ch11", b11);
                postValues.put("ch12", b12);
                postValues.put("ch13", b13);
                postValues.put("ch14", b14);
                postValues.put("ch15", b15);
                postValues.put("ch16", b16);
                postValues.put("ch17", b17);
                postValues.put("ch18", b18);
                postValues.put("ch19", b19);
                postValues.put("ch20", b20);

                postValues.put("ch21", b21);
                postValues.put("ch22", b22);
                postValues.put("ch23", b23);
                postValues.put("ch24", b24);

                postValues.put("text", text);


               tab1Ref.document(data).set(postValues)
                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                             System.out.println("성공");
                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               System.out.println("실패");
                           }
                       });

              //  DatabaseReference keyRef = fdRef.child(key);
                // fdRef의 자식 키값을 가리킨다.
                // keyRef.setValue(postValues);
                // 클래스

                Toast.makeText(getApplicationContext(), "저장완료", Toast.LENGTH_SHORT).show();
                // 액티비티 종료코드 추가
                finish();


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 액티비티 종료코드 추가
                finish();

            }
        });
    }

    }

