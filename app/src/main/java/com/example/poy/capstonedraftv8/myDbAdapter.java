package com.example.poy.capstonedraftv8;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(long date_time,int color, String event,String date_start, String time_event, int date_id)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.DATE_TIME, date_time);
        contentValues.put(myDbHelper.COLOR, color);
        contentValues.put(myDbHelper.EVENT, event);
        contentValues.put(myDbHelper.DATE_START, date_start);
        contentValues.put(myDbHelper.TIME_EVENT, time_event);
        contentValues.put(myDbHelper.DATE_ID, date_id);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME,null);
        return res;

    }

    public Cursor getEventData(String date_start) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_START+"=?",new String []{date_start});
        return res;

    }

// GETTING START DATE
    public Cursor getStartDate(String date_id){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={date_id};

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_ID+"=? order by "+myDbHelper.DATE_START+" asc limit 1",whereArgs);
        return res;
    }

// GETTING END DATE
    public Cursor getEndDate(String date_id){
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_ID+"=? order by "+myDbHelper.DATE_START+" desc limit 1",new String []{date_id});
        return res;
    }

// GETTING EVENT INFO
    public Cursor getEventInfo(String date_id){
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_ID+"=?",new String []{date_id});
        return res;
    }



    public Cursor getDateIdMax() {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select COALESCE(max("+ myDbHelper.ID+"),0) from "+ myDbHelper.TABLE_NAME,null);
        return res;

    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.ID, myDbHelper.DATE_TIME, myDbHelper.COLOR, myDbHelper.EVENT, myDbHelper.DATE_START, myDbHelper.TIME_EVENT, myDbHelper.DATE_ID};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.ID));
            String date_time =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE_TIME));
            String  color =cursor.getString(cursor.getColumnIndex(myDbHelper.COLOR));
            String  event =cursor.getString(cursor.getColumnIndex(myDbHelper.EVENT));
            String  date_start =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE_START));
            String  time_event =cursor.getString(cursor.getColumnIndex(myDbHelper.TIME_EVENT));
            String  date_id =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE_ID));
            buffer.append(cid+ "   " + date_time + "   " + color +"  " + event +"  "+date_start+" "+time_event+" "+date_id+"\n");
        }
        return buffer.toString();
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME , myDbHelper.DATE_ID+" = ?",whereArgs);
        return  count;
    }

    public int updateEvent(long date_time,int color, String event, String date_start, String time_event, String date_id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.DATE_TIME, date_time);
        contentValues.put(myDbHelper.COLOR, color);
        contentValues.put(myDbHelper.EVENT, event);
        contentValues.put(myDbHelper.DATE_START, date_start);
        contentValues.put(myDbHelper.TIME_EVENT, time_event);
        String[] whereArgs= {date_id};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.DATE_ID+" = ?",whereArgs );
        return count;
    }


    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String ID="_id";     // Column I (Primary Key)
        private static final String DATE_TIME = "date_time";    //Column II
        private static final String COLOR= "color";    // Column III
        private static final String EVENT= "event";    // Column III
        private static final String DATE_START= "date_start";    // Column III
        private static final String TIME_EVENT= "time_event";    // Column III
        private static final String DATE_ID= "date_id";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DATE_TIME+" BIGINT(99) ,"+ COLOR+" INTEGER,"+ EVENT+" VARCHAR(225),"+ DATE_START+" VARCHAR(225),"+ TIME_EVENT+" VARCHAR(225),"+ DATE_ID+" INTEGER);";


        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }


        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
}