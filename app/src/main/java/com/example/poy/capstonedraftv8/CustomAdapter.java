package com.example.poy.capstonedraftv8;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private ArrayList<DataModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {

        TextView color;
        TextView name;
        TextView time;
        ImageView info;
        TextView crop;
        ImageView crop_info;

    }



    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {


        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;




        switch (v.getId())
        {

            case R.id.info:

                Snackbar.make(v, "" +dataModel.getEvent_id()+" "+dataModel.getDate_id(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;


        }


    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.color=(TextView)convertView.findViewById(R.id.color);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.crop = (TextView) convertView.findViewById(R.id.crop);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.info);
            viewHolder.crop_info = (ImageView) convertView.findViewById(R.id.crop_info);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.color.setBackgroundColor(dataModel.getColor());
        viewHolder.name.setText("\t"+dataModel.getEvent_name());
        viewHolder.time.setText("\t"+dataModel.getEvent_time());

        viewHolder.crop.setText("\t"+dataModel.getCrop_name()+" "+dataModel.getVariety());
        String crop_type=dataModel.getCrop();
        if(crop_type.equalsIgnoreCase("rice"))
        {
            viewHolder.crop_info.setImageResource(R.drawable.ricev2);
        }
        else if (crop_type.equalsIgnoreCase("onion"))
        {
            viewHolder.crop_info.setImageResource(R.drawable.onionv2);
        }

        int a = dataModel.getIcon();

        if(a==1)
        {
         viewHolder.info.setImageResource(R.drawable.land_iconv2);

        }
        else if(a==2)
        {
            viewHolder.info.setImageResource(R.drawable.crop_iconv2);
        }
        else if(a==3)
        {
            viewHolder.info.setImageResource(R.drawable.care_iconv2);
        }
        else if(a==4)
        {
            viewHolder.info.setImageResource(R.drawable.pest_iconv2);
        }
        else if(a==5)
        {
            viewHolder.info.setImageResource(R.drawable.harvest_iconv2);
        }
        else if(a==6)
        {
            viewHolder.info.setImageResource(R.drawable.others_iconv2);
        }
        viewHolder.info.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }


}
