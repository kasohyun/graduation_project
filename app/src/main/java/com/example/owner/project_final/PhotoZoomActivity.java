package com.example.owner.project_final;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


public class PhotoZoomActivity extends AppCompatActivity {
    //[오투잡] 2019.04.03 이미지 줌 하는 부분. PhotoView 에서 줌 관련 기능을 제공하기떄문에 Glide 에 이미지랑 Url 만 삽입하면된다.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        Intent intent = getIntent();
        if (intent != null) {
            Uri uri = Uri.parse(intent.getStringExtra("photo"));
            L.e(":::uri : " + uri);
            PhotoView photoView = findViewById(R.id.iv_photo);
            Glide.with(getApplicationContext())
                    .load(uri)
                    //.signature(new StringSignature(""))
                    //.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(photoView);
        }
    }
}
