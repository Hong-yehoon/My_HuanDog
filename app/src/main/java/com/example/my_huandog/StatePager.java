package com.example.my_huandog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


public class StatePager extends Fragment {


    TextView lastDay, lastTime;

    SQLiteDatabase sqlDB;
    SQLiteDatabaseOpenHelper helper;

    String sql;
    Cursor cur;

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
        cur.moveToLast();

        if(cur !=null && cur.moveToLast()){
            String tt = cur.getString(0);
            String dd = cur.getString(1);
            lastDay.setText(dd);
            lastTime.setText(tt);
        }

        cur.close();
        helper.close();

        return viewGroup;
    }

}
