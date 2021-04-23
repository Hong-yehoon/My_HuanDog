package com.example.my_huandog;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class StartCount extends Activity {

    Intent startIntent;
    private TextView count;
    int num = 3;

    boolean won, gon;
    MediaPlayer mediaPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_count);

        startIntent = new Intent(StartCount.this, StartActivity.class);
        count = (TextView) findViewById(R.id.count);
        count.setText(String.valueOf(num));
        CountHandler.postDelayed(autoCount,1000);


        won = getIntent().getBooleanExtra("walk", false);
        gon = getIntent().getBooleanExtra("gps", false);

        startIntent.putExtra("walk",won);
        startIntent.putExtra("gps",gon);


    }

    private Handler CountHandler = new Handler();
    private Runnable autoCount = new Runnable() {
        @Override
        public void run() {
            num--;
            count.setText(String.valueOf(num));

            if (num > 1) {
                CountHandler.postDelayed(autoCount, 1000);
            } else {
                if (autoCount != null) {
                    startActivityForResult(startIntent,100);
                    //startActivity(startIntent);
                    StartCount.this.finish();
                }
            }
        }

    };

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        // 화면에서 넘어갈 떄 뒤로가기 버튼 못누르게에ㅔㅔㅔ
    }

}