package com.example.my_huandog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.timepicker.TimeFormat;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


public class StatePager extends Fragment {


    TextView lastDay, lastTime;

    SQLiteDatabase sqlDB;
    SQLiteDatabaseOpenHelper helper;

    String sql;
    Cursor cur;
    BarChart chart;
    BarData barData;

    //chart DB
    String [] testDayArrStr;
    int [] testTimeArr;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.home_state,container,false);

        lastDay = (TextView)viewGroup.findViewById(R.id.lastDay);
        lastTime = (TextView)viewGroup.findViewById(R.id.lastTime);


        helper = new SQLiteDatabaseOpenHelper(getContext(), SQLiteDatabaseOpenHelper.table,null,1);
        sqlDB = helper.getReadableDatabase();
        sql = "SELECT walkTime , walkDay FROM "+ helper.table;
        cur = sqlDB.rawQuery(sql, null);
        //cur.moveToLast();

        if(cur !=null && cur.moveToLast()){
            int tt = cur.getInt(0);
            String dd = cur.getString(1);
            if(tt>60){

                int min = tt/60;
                int sec = tt%60;

                lastTime.setText(min+"분"+sec+"초");

            }else{
                lastTime.setText(tt+"초");
            }

            lastDay.setText(dd);

        }

        cur.close();

        //**********chart에 쓸 날짜, 시간 SQLite에서 가져오기
        Cursor cur01;
        String sql01 = "Select Sum(walkTime), walkDay FROM "+helper.table+" Group by walkDay order by walkDay desc Limit 7 ";
        cur01 = sqlDB.rawQuery(sql01,null);
        sqlDB = helper.getReadableDatabase();

        //***************Chart에 SQlite DB 띄우기
        chart = (BarChart)viewGroup.findViewById(R.id.timeChart);

        //chart에 넣을DB ( 시간, 날짜 )
        testTimeArr = new int [7];
        testDayArrStr = new String [7];

        String str;
        ArrayList<String> labels_day = new ArrayList<>();

        int i=0;
            while (cur01.moveToNext()){
                testTimeArr[i] = cur01.getInt(0);
                testDayArrStr[i] = cur01.getString(1);

                str = testDayArrStr[i].substring(testDayArrStr[i].lastIndexOf("월")+1);
                labels_day.add(str);

                //Log.w("testtime",labels_day);
                Log.w("testtime",testTimeArr[i]+"");
                i++;
            }

        cur01.close();
        helper.close();

        //barChart
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0,testTimeArr[6]));
        barEntries.add(new BarEntry(1,testTimeArr[5]));
        barEntries.add(new BarEntry(2,testTimeArr[4]));
        barEntries.add(new BarEntry(3,testTimeArr[3]));
        barEntries.add(new BarEntry(4,testTimeArr[2]));
        barEntries.add(new BarEntry(5,testTimeArr[1]));
        barEntries.add(new BarEntry(6,testTimeArr[0]));

        // data 설정
        BarDataSet barDataSet = new BarDataSet(barEntries,"TimeData_chart");
        barDataSet.setColors(R.drawable.signpage_bg_color);
        barDataSet.setValueTextSize(13f);

        Collections.reverse(labels_day);
        // 라벨
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels_day));

        barData = new BarData();

        barData.addDataSet(barDataSet);

        chart.setData(barData);
        chart.setPinchZoom(false);
        chart.invalidate();

        return viewGroup;

    }



}


