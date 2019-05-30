package com.example.owner.project_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toolbar;

public class GameActivity extends AppCompatActivity {
    private GameView mView;
    Intent intent;
    Toolbar tb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new GameView(this);
        setContentView(mView);

        }


    @Override
    protected void onPause() {
        super.onPause();
        mView.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mView.start();
    }
}

