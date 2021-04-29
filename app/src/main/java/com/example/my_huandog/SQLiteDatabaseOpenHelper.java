package com.example.my_huandog;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDatabaseOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    public static final String table01 = "human";
    public static final String table02 = "dog";
    public static final String table = "walk";


    public SQLiteDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableTime(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists walk");
    }
    public void createTableTime (SQLiteDatabase db){
        String sql = "CREATE TABLE if not exists " + table+"(" +
                "walkTime int ," +
                "walkDay date)";
        try{
            db.execSQL(sql);
        }catch(SQLException e){

        }
    }

    public void insertTime (SQLiteDatabase db, int walkTime, String walkDay){
        db.beginTransaction();

        try{
            String sql = "INSERT INTO "+table+ "(walkTime, walkDay)"+
                    "values ('"+walkTime+"','"+walkDay+"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
}
