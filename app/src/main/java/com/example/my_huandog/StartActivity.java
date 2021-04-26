package com.example.my_huandog;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class StartActivity extends AppCompatActivity {

    TextView time;
    Button stop;

    private Boolean isRunning = true;
    private Thread timeThread = null;

    TextView Won, Gon;
    Boolean won, gon;

    AlertDialog.Builder setStop;

    SQLiteDatabase sqlDB;
    SQLiteDatabaseOpenHelper helper;
    String walkTime, walkDay;

    Intent intent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        intent = new Intent(StartActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        time = (TextView)findViewById(R.id.timeView);
        stop = (Button)findViewById(R.id.sStop);

        timeThread = new Thread(new timeThread());
        timeThread.start();


        Won = (TextView)findViewById(R.id.Won);
        Gon = (TextView)findViewById(R.id.Gon);


        won = getIntent().getBooleanExtra("walk", false);
        gon = getIntent().getBooleanExtra("gps", false);

        setStop = new AlertDialog.Builder(this);

        helper = new SQLiteDatabaseOpenHelper(StartActivity.this, SQLiteDatabaseOpenHelper.table, null, 1);


        if (won == true){
            Won.setTextColor(Color.parseColor("#4CAF50"));
            Won.setText("On");

        }
        if(gon==true){
            Gon.setTextColor(Color.parseColor("#4CAF50"));
            Gon.setText("On");
        }


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stop.setTextColor(Color.RED);

                isRunning = !isRunning;
                setStop.setMessage("산책한 시간 : "+time.getText().toString()+"\n 산책을 종료 하시겠습니까?");

                setStop.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isRunning = !isRunning;
                        stop.setTextColor(Color.WHITE);
                    }
                });
                //yes
                setStop.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ContentValues values;           //하나의 레코드를 정의하는 객체, 레코드 삽입과 갱신에 사용 - write
                        sqlDB = helper.getWritableDatabase();

                        long now = System.currentTimeMillis();
                        Date mDate = new Date(now);
                        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy"+"년"+"MM"+"월"+"dd"+"일");

                        walkDay = simpleDate.format(mDate);
                        walkTime = time.getText().toString();

                        helper.insertTime(sqlDB, walkTime, walkDay);
                        sqlDB.close();
                        timeThread.interrupt();


                        startActivity(intent);
                        finish();
                    }
                });

                AlertDialog alertDialog = setStop.create();
                alertDialog.show();

            }
        });

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 / 100) / 360;
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d", hour, min, sec);
            time.setText(result);


        }
    };

    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = 0;

            while (true) {
                while (isRunning) {  //Stop를 누르면 멈춤
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                time.setText("");
                                time.setText("00:00:00");
                            }
                        });
                        return; // 인터럽트 받을 경우 return
                    }
                }
            }
        }

    }
}