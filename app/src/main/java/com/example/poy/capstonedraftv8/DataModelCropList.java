package com.example.poy.capstonedraftv8;

public class DataModelCropList {

    int id;
    String crop;
    String crop_name;
    String variety;



    public DataModelCropList( int id,String crop,String crop_name,String variety)
    {

        this.id=id;
        this.crop=crop;
        this.crop_name=crop_name;
        this.variety=variety;
    }


    public int getId(){return id;}

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

