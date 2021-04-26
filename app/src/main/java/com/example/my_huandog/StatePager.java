package com.example.my_huandog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;


public class StatePager extends Fragment {


    TextView lastDay, lastTime;

    SQLiteDatabase sqlDB;
    SQLiteDatabaseOpenHelper helper;

    String sql;
    Cursor cur;
    BarChart chart;

    public final static String[] days = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun",};



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.home_state,container,false);

        lastDay = (TextView)viewGroup.findViewById(R.id.lastDay);
        lastTime = (TextView)viewGroup.findViewById(R.id.lastTime);

        chart = (BarChart)viewGroup.findViewById(R.id.timeChart);
        chart.setDrawGridBackground(false);

        BarDataSet barDataSet = new BarDataSet(data(),"testData");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData();
        barData.addDataSet(barDataSet);

        chart.setData(barData);
        chart.setPinchZoom(false);
        //chart.invalidate();
       sqlDB = helper.getReadableDatabase();
        sql = "SELECT walkTime , walkDay FROM "+ helper.table;


        helper = new SQLiteDatabaseOpenHelper(getContext(), SQLiteDatabaseOpenHelper.table,null,1);
        cur = sqlDB.rawQuery(sql, null);
        cur.moveToLast();

        if(cur !=null && cur.moveToLast()){
            String tt = cur.getString(0);
            String dd = cur.getString(1);
            lastDay.setText(dd);
            lastTime.setText(tt);
        }

        cur.close();
        helper.close();

        Cursor cur01;

        cur01 = sqlDB.rawQuery("Select walkTime, walkDaty FROM "+helper.table,null);
        if(cur !=null && cur.moveToLast()){
            String tt = cur.getString(0);
            String dd = cur.getString(1);

        }


        return viewGroup;



    }
    private ArrayList<BarEntry> data (){
        ArrayList<BarEntry> data_val = new ArrayList<>();
        data_val.add(new BarEntry(1,10));
        data_val.add(new BarEntry(2,20));
        data_val.add(new BarEntry(3,25));
        data_val.add(new BarEntry(4,5));
        data_val.add(new BarEntry(5,0));
        data_val.add(new BarEntry(6,30));
        data_val.add(new BarEntry(7,27));
        return data_val;

    }


}


