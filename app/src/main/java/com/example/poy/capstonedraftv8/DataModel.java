package com.example.poy.capstonedraftv8;

public class DataModel {

    String event_name;
    String event_time;
    String event_id;
    String date_id;
    int color;
    int icon;
    String crop;
    String crop_name;
    String variety;



    public DataModel( String event_name,String event_time,String event_id,String date_id,int color,int icon,String crop,String crop_name,String variety)
    {

        this.event_name=event_name;
        this.event_time=event_time;
        this.event_id=event_id;
        this.date_id=date_id;
        this.color=color;
        this.icon=icon;
        this.crop=crop;
        this.crop_name=crop_name;
        this.variety=variety;
    }


    public String getEvent_name()
    {
        return event_name;
    }

    public String getEvent_time()
    {
        return event_time;
    }

    public String getEvent_id()
    {
        return event_id;
    }

    public String getDate_id()
    {
        return date_id;
    }

    public int getColor(){return color;}

    public int getIcon(){return icon;}

    public String getCrop()
    {
        return crop;
    }

    public String getCrop_name()
    {
        return crop_name;
    }

    public String getVariety()
    {
        return variety;
    }



}
